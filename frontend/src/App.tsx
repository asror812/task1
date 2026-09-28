import { useEffect, useState } from 'react'
import keycloak, { loginWithDebug, replayOAuthDebugEvents } from './keycloak'

type Order = {
  id: number
  orderNumber: number
  status: string
  totalAmount: number | null
  deliveryCity: string | null
  deliveryAddress: string | null
}

type TokenInfo = {
  accessTokenExpiresAt: string
  refreshTokenExpiresAt: string
  lastCheck: string
  result: string
}

function formatExpiry(exp?: number) {
  return exp ? new Date(exp * 1000).toLocaleTimeString() : 'неизвестно'
}

function App() {
  const [initialized, setInitialized] = useState(false)
  const [authenticated, setAuthenticated] = useState(false)
  const [orders, setOrders] = useState<Order[]>([])
  const [message, setMessage] = useState('')
  const [tokenInfo, setTokenInfo] = useState<TokenInfo | null>(null)
  const redirectUri = `${window.location.origin}/`

  function updateTokenInfo(result: string) {
    setTokenInfo({
      accessTokenExpiresAt: formatExpiry(keycloak.tokenParsed?.exp),
      refreshTokenExpiresAt: formatExpiry(keycloak.refreshTokenParsed?.exp),
      lastCheck: new Date().toLocaleTimeString(),
      result,
    })
  }

  useEffect(() => {
    const authorizationCode = new URLSearchParams(
      window.location.hash.startsWith('#') ? window.location.hash.slice(1) : window.location.search,
    ).get('code')
    if (authorizationCode) {
      console.info('2. Keycloak redirected back with this one-time authorization code:', authorizationCode)
    }

    keycloak.onAuthSuccess = () => {
      console.info('3. keycloak-js sent the real authorization code + code_verifier to Keycloak and received tokens.')
    }
    keycloak.onAuthRefreshSuccess = () => {
      console.info('Access token was refreshed successfully.')
    }
    keycloak.onTokenExpired = () => {
      console.info('Access token expired. The next protected request will try to refresh it.')
    }

    keycloak
      .init({
        onLoad: 'check-sso',
        pkceMethod: 'S256',
        checkLoginIframe: false,
      })
      .then((isAuthenticated) => {
        setAuthenticated(isAuthenticated)
        if (isAuthenticated) {
          replayOAuthDebugEvents()
          updateTokenInfo('JWT получен после входа в Keycloak.')
        }
        setInitialized(true)
      })
      .catch(() => {
        setMessage('Не удалось подключиться к Keycloak.')
        setInitialized(true)
      })
  }, [])

  async function loadMyOrders() {
    try {
      const refreshed = await keycloak.updateToken(30)
      console.info(
        refreshed
          ? 'Access token was refreshed before the API request.'
          : 'Access token is still valid; no refresh was needed.',
      )
      updateTokenInfo(
        refreshed
          ? 'Access token обновлён с помощью refresh token.'
          : 'Access token ещё действителен, обновление не требуется.',
      )

      const response = await fetch('/api/orders/my', {
        headers: {
          Authorization: `Bearer ${keycloak.token}`,
        },
      })

      console.info(`Sent a protected request to /api/orders/my. HTTP status: ${response.status}`)

      if (!response.ok) {
        throw new Error(`API returned ${response.status}`)
      }

      setOrders(await response.json())
      setMessage('')
    } catch {
      setMessage('Не удалось загрузить заказы. Проверь роль CUSTOMER и связь Customer с Keycloak user.')
    }
  }

  if (!initialized) {
    return <main className="page">Подключение к Keycloak…</main>
  }

  if (!authenticated) {
    return (
      <main className="page">
        <section className="card">
          <p className="eyebrow">Order Management</p>
          <h1>Вход в систему</h1>
          <p>Вход выполняется на странице Keycloak. Пароль не попадает во frontend.</p>
          <button onClick={() => loginWithDebug(redirectUri)}>
            Login with Keycloak
          </button>
          {message && <p className="error">{message}</p>}
        </section>
      </main>
    )
  }

  return (
    <main className="page">
      <section className="card">
        <p className="eyebrow">Order Management</p>
        <h1>Привет, {keycloak.tokenParsed?.preferred_username ?? 'user'}</h1>
        <p>{keycloak.tokenParsed?.email ?? 'Email отсутствует'}</p>

        <div className="actions">
          <button onClick={loadMyOrders}>Мои заказы</button>
          <button className="secondary" onClick={() => keycloak.logout({ redirectUri })}>
            Logout
          </button>
        </div>

        {message && <p className="error">{message}</p>}

        {tokenInfo && (
          <section className="token-status">
            <h2>Что происходит с токенами</h2>
            <p><strong>Access token действует до:</strong> {tokenInfo.accessTokenExpiresAt}</p>
            <p><strong>Refresh token действует до:</strong> {tokenInfo.refreshTokenExpiresAt}</p>
            <p><strong>Последняя проверка:</strong> {tokenInfo.lastCheck}</p>
            <p className="token-result">{tokenInfo.result}</p>
            <small>Токены не показываются в интерфейсе, потому что это секретные данные.</small>
          </section>
        )}

        {orders.length > 0 && (
          <ul className="orders">
            {orders.map((order) => (
              <li key={order.id}>
                <strong>Заказ #{order.orderNumber}</strong>
                <span>{order.status}</span>
                <span>{order.totalAmount ?? 0}</span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  )
}

export default App
