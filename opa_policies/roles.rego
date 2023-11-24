package dnb.userregistration.roles

# Input Document:
#
# input.user.claims
# input.user.attributes.hr_data
# input.user.attributes.authorizations
# input.user.attributes.realm_access.roles

import future.keywords.in
import future.keywords.if

# Can be used if token verification is enabled.
import data.dnb.userregistration.token

default allow_admin := false
default allow_user := false
default allow_admin_from_api := false
default allow_user_from_api := false

allow_user {
    input.method == "GET"
    input.user.attributes.authorizations[_] == "dnb.userregistration.read"
    not overrides
}

allow_user_from_api {
    input.method == "GET"
    ### Can be used if token verification is enabled.
    token.decode_verify.payload.authorizations[_] == "dnb.userregistration.read"
    not overrides
}

allow_admin {
    input.method in {"GET", "POST", "PUT", "DELETE"}
    input.user.attributes.authorizations[_] == "dnb.userregistration.write"
    not overrides
}

allow_admin_from_api {
    input.method in {"GET", "POST", "PUT", "DELETE"}
    ### Can be used if token verification is enabled.
    token.decode_verify.payload.authorizations[_] == "dnb.userregistration.write"
    not overrides
}

overrides := get_overrides {
    get_overrides := input.user.claims.hr_data[_] == "long_term_sicknes"
    allow_user := true

    # Can be used if token verification is enabled.
    #get_overrides := token.decode_verify.payload.hr_data[_] == "parental leave"
}