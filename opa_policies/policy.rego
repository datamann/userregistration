package dnb.userregistration.auth

import data.dnb.userregistration.token
import data.dnb.userregistration.roles
import data.dnb.userregistration.rosterlist


#import future.keywords.in
#import stdlib.io
#my_css = File.content("css/file.css")

default allow := false

### Admins
allow {

    ### Cheking token validity
    token_allow

    ### Checking users roles
    admin_role_allow

    ### Only if on rosterlist
    rosterlist.allow
}

### Users
allow {

    ### Cheking token validity
    token_allow

    ### Checking users roles
    user_role_allow

    ### Only if on rosterlist
    rosterlist.allow
}

### API Admins
allow {

    ### Cheking token validity
    token_allow

    ### Checking users roles
    admin_api_allow

    ### Only if on rosterlist
    rosterlist.allow
}

### API Users
allow {

    ### Cheking token validity
    token_allow

    ### Checking users roles
    user_api_allow

    ### Only if on rosterlist
    rosterlist.allow
}

### Allow imports
token_allow {
	token.allow
}

admin_role_allow {
    roles.allow_admin
}

user_role_allow {
    roles.allow_user
}

admin_api_allow {
    roles.allow_admin_from_api
}

user_api_allow {
    roles.allow_user_from_api
}