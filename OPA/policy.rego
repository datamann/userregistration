package actions

import data.actions.eval

default allow := false


# Sjekk: https://play.openpolicyagent.org/p/ja1qfh7fMX
# https://play.openpolicyagent.org/p/7MWD9tTwKP
# https://play.openpolicyagent.org/p/zwil29Zjno
# curl -X POST http://localhost:8181/v1/data/actions -d @./test_input.json -H 'Content-Type: application/json'

allow {
    eval.allow_write
    eval.not_denied
}
