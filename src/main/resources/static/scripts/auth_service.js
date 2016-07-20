angular.module(MODULE_NAME).factory('Auth', function () {
    var user

    return {
        setUser : function (aUser) {
            user = aUser;
        },
        isLoggedIn : function () {
            return (user)? user: false;
        },
        isAdmin : function () {
            return user.isAdmin;
        }
    }
})