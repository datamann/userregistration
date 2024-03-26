package dnb.userregistration

import data.dnb.userregistration.eval
import data.dnb.userregistration.token

default allow := false

# curl -X POST http://localhost:8181/v1/data/dnb/userregistration/rule -d @./input.json -H 'Content-Type: application/json'

allow {
    eval.allow
    eval.not_denied
    token_allow
}

# Token verification
token_allow {
	token.allow
}