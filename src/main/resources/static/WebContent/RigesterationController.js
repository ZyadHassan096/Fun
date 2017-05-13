var app = angular.module("phase2" , ['ngRoute'])

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
    $routeProvider
		.when("http://localhost:8060/TheAngular_Project/LogInPage.html", {
			templateUrl: "LogInPage.html",
			controller: "LogIn"
		})
		.when("http://localhost:8060/TheAngular_Project/StudentPage.html", {
			templateUrl: "StudentPage.html",
			controller: "Load"
		})
		.when("http://localhost:8060/TheAngular_Project/TeacherPage.html", {
			templateUrl: "TeacherPage.html",
			controller: "Load"
		})
		.when("http://localhost:8060/TheAngular_Project/SignUpPage.html", {
			templateUrl: "SignUpPage.html",
			controller: "SignUP"
		})
		// .otherwise({ redirectTo: '/'})
		;
}]);


app.controller( "SignUP" ,function ($scope , $http , srvShareData , $location)
		{
			$scope.dataToShare = [];
			$scope.save = function() {
				var email= document.getElementById("email").value;
				var UName=document.getElementById("UName").value;
				var Pass=document.getElementById("Pass").value;
				var genders=document.getElementById("UserGender");
				var gender=genders.elements["UserGender"].value ;
				var types=document.getElementById("UserType");
				var UserType=types.elements["UserType"].value;
								
				$http.get('http://localhost:8090/SignUp/'+email+'/'+UName+'/'+Pass+'/'+gender+'/'+UserType)
				.then(function(response)
					{
					});
				if (UserType=="t")
				{
				window.location.href="http://localhost:8060/TheAngular_Project/TeacherPage.html";
				}
				else if (UserType=="s")
				{
				window.location.href="http://localhost:8060/TheAngular_Project/StudentPage.html";
				}
				}
		});


app.controller( "LogIn" , function ($scope , $http , srvShareData , $location)
		{
			$scope.dataToShare = [];
			$scope.saveData = function() {
				var email= document.getElementById("email").value;
				var Pass=document.getElementById("Pass").value;
				var Info ;
				$http.get('http://localhost:8090/LogIn/'+email+'/'+Pass)
				.then(function(response)
					{
						Info = JSON.stringify(response.data);
						
						$scope.dataToShare = JSON.parse(Info) ;
						srvShareData.addData($scope.dataToShare);
						if ($scope.dataToShare[0].type=="t")
							{
							window.location.href="http://localhost:8060/TheAngular_Project/TeacherPage.html";
							}
						else if($scope.dataToShare[0].type=="s")
							{
							window.location.href="http://localhost:8060/TheAngular_Project/StudentPage.html";
							}
						
					});
				}
		});


app.controller("Load" , function($scope,  srvShareData )
		{
				
				$scope.achievements = srvShareData.getData();
		});


app.service('srvShareData', function($window) {
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


