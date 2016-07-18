angular.module(MODULE_NAME).controller('UserController', 
    [ '$scope', '$http', 'Auth', function ($scope, $http, Auth) {
 
    	function getLoginTX(username, password, location) {
//    	    return (location === undefined)? 
//    	        `{"username": ${username}, "password": ${password}}` :
//    	        `{"username": ${username}, "password": ${password}, "location": ${Location}}`
    		return '{"username":"Bruce Lee","password":"hawaaa!!"}'
    	}
    	    	
    	$scope.login = function () {
    		let user =  getLoginTX(username, password)
            $http.post(SPRING_LOGIN_URI, user).then((tx_response) => {
            	if((tx_response.data.fieldType === "Login") && tx_response.data.field){
                	Auth.setUser(user) //Update the state of the user in the app     
                	console.log('bingo')
            	}
            })
        }
    }]
)