package dnb.userregistration.accessoverride

import future.keywords.if

### Read from rosterlist.json
accessoverride := data.access_override

default allow := false

# Allow admins to do anything.
allow if user_is_admin
allow if user_is_user

user_is_admin if "admin" in data.user_roles[input.user]
user_is_user if "user" in data.user_roles[input.user]