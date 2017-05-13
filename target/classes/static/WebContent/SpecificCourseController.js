var app = angular.module("phase2" , ['ngRoute'])

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
    $routeProvider
		.when("http://localhost:8060/TheAngular_Project/SpecificCourse.html", {
			templateUrl: "SpecificCourse.html",
			controller: "ShowGames"
		})
		.when("http://localhost:8060/TheAngular_Project/GamesPage.html", {
			templateUrl: "GamesPage.html",
			controller: "LoadGames"
		})
		
		// .otherwise({ redirectTo: '/'})
		;
}]);

app.controller( "ShowGames" ,function ($scope , $http ,ShareGames , $location)
		{
			$scope.dataToShare = [];
			var Info ;
			$scope.saveGames = function() 
			{
				var CourseName=document.getElementById("CName").value;
						
				$http.get('http://localhost:8090/ShowGames/'+CourseName)
				.then(function(response)
					{
					Info = JSON.stringify(response.data);
					$scope.dataToShare = JSON.parse(Info) ;
					ShareGames.addData($scope.dataToShare);
					window.location.href="http://localhost:8060/TheAngular_Project/GamesPage.html";
					});
				
			}
		});

app.controller("LoadGames" , function($scope,  ShareGames )
		{
		
				$scope.Games = ShareGames.getData();
		});


app.service('ShareGames', function($window) {
    var KEY = 'App.SelectedValue';

    var addData = function(newObj) {
    	//$window.sessionStorage.clear();
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        } else {
            mydata = [];
        }
        mydata = newObj;
       
        $window.sessionStorage.setItem(KEY, JSON.stringify(mydata));
    };

    var getData = function(){
        var mydata = $window.sessionStorage.getItem(KEY);
        if (mydata) {
            mydata = JSON.parse(mydata);
        }
        
        return mydata || [];
    };

    return {
        addData: addData,
        getData: getData
    };
});
