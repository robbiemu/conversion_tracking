angular.module(MODULE_NAME).controller('HomeController', ['$location', 'Res',  function($location, Res){
	if($location.path() ===  "/"){
		Res.style(['styles/normalize.css', 'styles/game.css'])
		Res.script(['http://www.bitstorm.org/jquery/shadow-animation/jquery.animate-shadow-min.js',  'scripts/game/game.js'])
//		Res.script(['http://www.bitstorm.org/jquery/shadow-animation/jquery.animate-shadow-min.js', 'scripts/game/audio.js', 'scripts/game/autoclick_agent.js', 'scripts/game/game.js'])
	}

}]);