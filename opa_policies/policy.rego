package dnb.userregistration.auth

import data.dnb.userregistration.auth.eval
import data.dnb.userregistration.token

default allow := false

# curl -X POST http://localhost:8181/v1/data/dnb/userregistration/auth -d @./input.json -H 'Content-Type: application/json'

allow {
    eval.allow_write
    eval.not_denied
    #token_allow
}

# Token verification
token_allow {
	token.allow
}
