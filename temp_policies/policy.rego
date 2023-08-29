package envoy.authz
import input.attributes.request.http as http_request

default allow = false

token = {"valid": valid, "payload": payload} {
    print(input.authorization)
    [_, encoded] := split(input.authorization, " ")
    [valid, _, payload] := io.jwt.decode_verify(encoded, {"secret": "secret"})
}

allow {
    is_token_valid
    action_allowed
}

is_token_valid {
  token.valid
  now := time.now_ns() / 1000000000
  token.payload.nbf <= now
  now < token.payload.exp
}

action_allowed {
  input.http_method == "GET"
  token.payload.role == "guest"
  glob.match("/people*", [], http_request.path)
}

action_allowed {
  input.http_method == "GET"
  token.payload.role == "admin"
  glob.match("/people*", [], http_request.path)
}

action_allowed {
  http_request.method == "POST"
  token.payload.role == "admin"
  glob.match("/people", [], http_request.path)
  lower(input.parsed_body.firstname) != base64url.decode(token.payload.sub)
}