import Keycloak from 'keycloak-js'

const debugEventsKey = 'order-management:oauth-debug-events'

const keycloak = new Keycloak({
  url: 'http://localhost:8080',
  realm: 'order-management',
  clientId: 'order-management-web',
})

type DebugEvent = {
  message: string
  details?: Record<string, string | undefined>
}

function saveDebugEvent(message: string, details?: Record<string, string | undefined>) {
  const event: DebugEvent = {
    message,
    details,
  }
  const events = JSON.parse(sessionStorage.getItem(debugEventsKey) ?? '[]') as DebugEvent[]
  events.push(event)
  sessionStorage.setItem(debugEventsKey, JSON.stringify(events))
  console.info(message, details)
}

export async function loginWithDebug(redirectUri: string) {
  const loginUrl = await keycloak.createLoginUrl({ redirectUri })
  const parameters = new URL(loginUrl).searchParams
  const state = parameters.get('state')
  const callbackState = state
    ? JSON.parse(localStorage.getItem(`kc-callback-${state}`) ?? '{}') as { pkceCodeVerifier?: string }
    : {}

  saveDebugEvent('1. Frontend created the PKCE authorization request.', {
    client_id: parameters.get('client_id') ?? undefined,
    redirect_uri: parameters.get('redirect_uri') ?? undefined,
    response_type: parameters.get('response_type') ?? undefined,
    code_challenge_method: parameters.get('code_challenge_method') ?? undefined,
    code_challenge: parameters.get('code_challenge') ?? undefined,
    code_verifier: callbackState.pkceCodeVerifier,
  })

  window.location.assign(loginUrl)
}

export function replayOAuthDebugEvents() {
  const events = JSON.parse(sessionStorage.getItem(debugEventsKey) ?? '[]') as DebugEvent[]
  events.forEach((event) => console.info(event.message, event.details))
  sessionStorage.removeItem(debugEventsKey)
}

export default keycloak
