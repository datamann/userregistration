package envoy.authz_test

import data.envoy.authz

test_allow {
    authz.allow with input.authorization as "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNjg3NTUyOTY4fQ.UaGEa_hV6DiUDxmDk08OYvtcD2TuneIWbZ7l2ns-1u0"
      with input.http_method as "GET" 
}
