package dnb.userregistration.token

# Postman (API) support
# input.user.tokenValue

default allow := false

allow {
	not jwt_token_expired
	input.user.claims.azp == "userregistration"
}

allow {
	# Can be used if token verification is enabled.
	decode_verify.payload.azp == "userregistration"
	decode_verify.isValid
}

# Can be used if token verification is enabled.
### Token from the user input ###
# bearer_token := t {
# 	v := input.user.idToken.tokenValue
# 	startswith(v, "Bearer ")
# 	t := substring(v, count("Bearer "), -1)
# }

bearer_token := v {
	v := input.user.tokenValue
}

### Can be used if token verification is enabled.
### Full JWKS Keys stringified ###
jwks := j {
	j := "{\"keys\":[{\"kid\":\"PiBW_GAdr1sIwH4280XpVSiY85kSsREdLt3M953tZ9w\",\"kty\":\"RSA\",\"alg\":\"RS256\",\"use\":\"sig\",\"n\":\"r4GQQj_5NyX0rBQavhG6BshGYiJaSm-WBJl7g1eE4PkM32kRlrwoAUoKVRVldoQAfmN7awOF4Q0-iZnKYECsQb1Ofv9POWWBVsueDOW0BugrRiRk2BoIJIcXuaFqspc3xyYPBzX7l9jux2LEAIrYb2wlrA48ppoanHfy8XHAwTprk4vCVzl7qpegQDC-Tjo_DTMbaG9Bj70Ne-fpYVFA7kl1uGUSxhMc69pmmFGvxcHPUMTb0DwbGq3lN7QGgXg06KE-27jqAMcUhLPpLzu6WvkWVYl8WAFkWRcZE-ZAL3ia7ORSy0RJ0Mg2RFE8OpyP9p1r4LnC_gHdJiVZ6kN7IQ\",\"e\":\"AQAB\",\"x5c\":[\"MIIClTCCAX0CBgGLAW6gMzANBgkqhkiG9w0BAQsFADAOMQwwCgYDVQQDDANETkIwHhcNMjMxMDA1MjAwMDA5WhcNMzMxMDA1MjAwMTQ5WjAOMQwwCgYDVQQDDANETkIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCvgZBCP/k3JfSsFBq+EboGyEZiIlpKb5YEmXuDV4Tg+QzfaRGWvCgBSgpVFWV2hAB+Y3trA4XhDT6JmcpgQKxBvU5+/085ZYFWy54M5bQG6CtGJGTYGggkhxe5oWqylzfHJg8HNfuX2O7HYsQAithvbCWsDjymmhqcd/LxccDBOmuTi8JXOXuql6BAML5OOj8NMxtob0GPvQ175+lhUUDuSXW4ZRLGExzr2maYUa/Fwc9QxNvQPBsareU3tAaBeDTooT7buOoAxxSEs+kvO7pa+RZViXxYAWRZFxkT5kAveJrs5FLLREnQyDZEUTw6nI/2nWvgucL+Ad0mJVnqQ3shAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGISy34SJJxoKjQ2N0tPjp4XObDYr1d+/Ygaegm9/MHO39hULHlIkl8ufDApUqQXZXRovL9jOiBAcxbkMffTIQxqldxuzrSDYcoGM5KnlevMocurjBHsRagSFNG7kpV1Q9THXUz4SYMOzPLrdpvzLLQp2ZdKgwUgY9Dii4W2jTD1ScDYCC8JVpTYT8sjaAvr7YkEMsPb6jEX9eJ6BFRPfaWJu08vqfjuYMWiBeUxJshmGK7qAeK9qAZ+lf3XK07DOcRyUeOSfxcMEM5nznXYCUKmBHYLnyspOeHrNXy0VG/TZT1H3rZyPlZRWjTLV+HuVo1KVm0TYN6bBtECW5oEmuA=\"],\"x5t\":\"HKD_UE-zg7iP_bUvGizKy3p_xe8\",\"x5t#S256\":\"DFbI_n64SDzw3aQ9H8QQHACGZKzvDOTUafB-BJkPxE4\"},{\"kid\":\"zpOPkHoZloU-IEKYUeMniF2OVs83FLPPefT_JlxUYF4\",\"kty\":\"RSA\",\"alg\":\"RSA-OAEP\",\"use\":\"enc\",\"n\":\"z9TQOwWgbeZ02n36gsOAgskK5rDT58Ytr6AiLnMiSxI2PWmryCPfiiiorCROIsM_sDc6rqYjfCqTan3l2ldnUdTxM3t-YoQj4IY6DgYnmxv3Zl3vli0el2FrmNLSUAPesoCBX2iIQFjHqZCvUwrfrAO7G_3y_FIjkv_MlkVr-biYcNlXgalKbKrsD9UqkCkw9QaSTdAzVl1Zvo7_4yGDqdqODpizT1PHt4zrLkuIXSoTxb7Ubffa8QUA5ZLxNb9WPLgG_-gBBBy66PwhQS6GUYjyDA78Z9sTLFQdaDZri8u_hA0nV7i_ekIa-DYAh5HPjwbdiog3vLLYawAQixxrjQ\",\"e\":\"AQAB\",\"x5c\":[\"MIIClTCCAX0CBgGLAW6gsDANBgkqhkiG9w0BAQsFADAOMQwwCgYDVQQDDANETkIwHhcNMjMxMDA1MjAwMDA5WhcNMzMxMDA1MjAwMTQ5WjAOMQwwCgYDVQQDDANETkIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDP1NA7BaBt5nTaffqCw4CCyQrmsNPnxi2voCIucyJLEjY9aavII9+KKKisJE4iwz+wNzqupiN8KpNqfeXaV2dR1PEze35ihCPghjoOBiebG/dmXe+WLR6XYWuY0tJQA96ygIFfaIhAWMepkK9TCt+sA7sb/fL8UiOS/8yWRWv5uJhw2VeBqUpsquwP1SqQKTD1BpJN0DNWXVm+jv/jIYOp2o4OmLNPU8e3jOsuS4hdKhPFvtRt99rxBQDlkvE1v1Y8uAb/6AEEHLro/CFBLoZRiPIMDvxn2xMsVB1oNmuLy7+EDSdXuL96Qhr4NgCHkc+PBt2KiDe8sthrABCLHGuNAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAFqyQeA/BgwtphgSWfycleMRlwx1P0NlS8WcJkLdwTXJr35lYrbKOM5nprMG/FyIOC34QK8nlpFnGGtvp0kiI+Tw++TD4yEUpB+K4gwH2V8EuKxE8fwwcTobN+tmf9vhm5mqgmbUu/vQFgrdMvUQZkor1dL0B517G2p1mv443FXTlqS17Mfyg5Hx4dRL5Rdi76IV9y+X4ZN5rNP7KyAAKLEjsrGZ3A2o3Q2to9SpLLWH9bU44/N+9DbtYX0Q8jInhAUlQKIt/PnZCXxaf12nXlQFx+Ohz7ixe4uZkVhq3six/Q7jArnnOC3WPmWhUGBTMtVEtQ5GCqVqM+PKUIPTOf8=\"],\"x5t\":\"Vafnyev6Kfdur-fgT6n166uBKE4\",\"x5t#S256\":\"ZjCVVvUSG6X3Qd88n9Ud2GUjMKiWhAlyWBm8GAviisA\"}]}"
}

### Can be used if token verification is enabled.
decode_verify = { "isValid": isValid, "header": header, "payload": payload } {
     [isValid, header, payload] := io.jwt.decode_verify(bearer_token, { "cert": jwks, "iss": "http://mykeycloak:8080/realms/DNB" })
}

### Can be used if token verification is enabled.
token_payload := decode_verify.payload

jwt_token_expired {
	token_payload.exp < time_now_s
}

time_now_s := time.now_ns() / 1000000000