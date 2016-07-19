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
                controller: 'AdminController',
                controllerAs: 'adminController',
                resolve: {
                    factory: function ($q, $rootScope, $location, $http, Auth) {
                    	loadURLs($rootScope, $http)
                    	checkRouting($q, $rootScope, $location, Auth)
                    }
                }
            })
            .when('/login', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
           .when('/login/:extension', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
            .when('/register', { redirectTo: '/login' })
            .otherwise('/')
        }
   ])

const loadURLs = function ($rootScope, $http) {
	$http.get(SPRING_LISTURLS_URI).then((tx_response) => {
		if(tx_response.status == 200) {
			$rootScope.URLs = tx_response.data			
		}
	})
}
   
const checkRouting = function ($q, $rootScope, $location, Auth) {
        if (!Auth.isLoggedIn()) {
            console.log(`${$location.path()} - route denied. User not logged in.`)
            event.preventDefault()
            $location.path('/login')
        }
}