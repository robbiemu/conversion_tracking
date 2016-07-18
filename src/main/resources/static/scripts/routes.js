//require('./constants')

angular.module(MODULE_NAME)
    .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/', {
                templateUrl: HOME_PAGE,
                controller: 'HomeController',
                controllerAs: 'homeController',
                resolve: {
                    factory: checkRouting
                }
            })
            .when('/admin', {
                templateUrl: ADMIN_PAGE,
                controller: 'HomeController',
                controllerAs: 'homeController',
                resolve: {
                    factory: checkRouting
                }
            })
            .when('/login', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
            .when('/register', { redirectTo: '/login' })
            .otherwise('/')
        }
   ])

const checkRouting = function ($q, $rootScope, $location, Auth) {
        if (!Auth.isLoggedIn()) {
            console.log(`${$location.path()} - route denied. User not logged in.`);
            event.preventDefault();
            $location.path('/login');
        }
        else {
            console.log(`${$location.path()} - routing, User is logged in. IF all this is working, remove this message.`);
        }
}