angular.module(MODULE_NAME).service('Res', ['$rootScope', function ($rootScope) {
	var scope = $rootScope
	
	return {
		style: function(inp) {
			let hrefs = []
			if(typeof inp === 'string') {
				hrefs.push(inp)
			} else {
				hrefs = inp
			}
			scope._styles=[]
			for(let i in hrefs){
				console.log("styling page with " + hrefs[i])
		  		let style = document.createElement('link')
		  		style.type = 'text/css'
		  		style.href = hrefs[i]
		  		style.rel = 'stylesheet'
		
				if(scope._rawStyles === undefined) {
					scope._rawStyles = []
				}
				scope._rawStyles.push(style)
		  		scope._styles.push( document.head.appendChild(style) )
		
				scope.$on('$destroy', function() {
					for (let key in scope._styles){
						scope._styles[key].parentNode.removeChild(scope._rawStyles[key])
						delete scope._styles[key]
						delete scope._rawStyles[key]
					}
				})			
			}
		},
		
		script: function(inp) {
			let hrefs = []
			if(typeof inp === 'string') {
				hrefs.push(inp)
			} else {
				hrefs = inp
			}
			scope._scripts=[]
			for(let i in hrefs){
				console.log("loading javascript: " + hrefs[i])
				let script = document.createElement('script')
				script.type = 'text/javascript'
				script.src = hrefs[i]
			
				if(scope._rawScripts === undefined) {
					scope._rawScripts = []
				}
				scope._rawScripts.push(script)
				scope._scripts.push( document.head.appendChild(script) )
			
				scope.$on('$destroy', function() {
					for (let key in scope._scripts){
						scope._scripts[key].parentNode.removeChild(scope._rawScripts[key])
						delete scope._scripts[key]
						delete scope._rawScripts[key]
					}
				})							
			}
		}	
	}
}])