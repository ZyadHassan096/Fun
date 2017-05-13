var app = angular.module("phase2" , ['ngRoute'])

app.config(["$routeProvider", "$locationProvider", function($routeProvider, $locationProvider){
    $routeProvider
		.when("http://localhost:8060/TheAngular_Project/PlayGameForm.html", {
			templateUrl: "PlayGameForm.html",
			controller: "PlayGame"
		})
		.when("http://localhost:8060/TheAngular_Project/TFGame.html", {
			templateUrl: "TFGame.html",
			controller: "LoadTF"
		})
		.when("http://localhost:8060/TheAngular_Project/MCQGame.html", {
			templateUrl: "MCQGame.html",
			controller: "LoadMCQ"
		})
		
		// .otherwise({ redirectTo: '/'})
		;
}]);


app.controller( "PlayGame" ,function ($scope , $http , srvShareData , $location)
		{
			$scope.save = function() {
				var GName=document.getElementById("GName").value;
				var TF = "TF";
				var MCQ="MCQ";
				$http.get('http://localhost:8090/GetSchema/'+GName)
				.then(function(response)
					{
					$scope.Schema = response.data;
					if ($scope.Schema==TF)
					{
						//alert("TF")
					$http.get('http://localhost:8090/Play_TFGame/'+GName).then(function(response)
							{
							$scope.Questions = JSON.stringify(response.data) ;
							$scope.TFQuestions = JSON.parse($scope.Questions) ;
							srvShareData.addData($scope.TFQuestions);
							window.location.href="http://localhost:8060/TheAngular_Project/TFGame.html";
							});
					}
					else if($scope.Schema==MCQ)
					{
						//alert("MCQ")
					$http.get('http://localhost:8090/Play_MCQGame/'+GName).then(function(response)
							{
							$scope.Questions = JSON.stringify(response.data) ;
							$scope.MCQQuestions = JSON.parse($scope.Questions) ;
							srvShareData.addData($scope.MCQQuestions);
							window.location.href="http://localhost:8060/TheAngular_Project/MCQGame.html";
							});
					}
					else // Invalid Game Name
					{
						alert("There is no Game With this name ")
					}
					
					});
				
				}
		});



app.controller("LoadTF" , function($scope,  srvShareData ,$http)
		{
				$scope.indexToShow = 0;
				$scope.TFQuestions = srvShareData.getData();
				$scope.score=0 ;
				$scope.change = function(){
					var ans=document.getElementById('answer'+JSON.stringify($scope.indexToShow)).value;
					if (ans==$scope.TFQuestions[$scope.indexToShow].Answer)
						{
						$scope.score = ($scope.score + 100) ;
						}
					
			        $scope.indexToShow = ($scope.indexToShow + 1) ;
			        if ($scope.indexToShow==$scope.TFQuestions.length)
			        	{
			        	$http.get('http://localhost:8090/SaveScore/'+$scope.score).then(function(response)
			        		{
			        		var Info = response.data ;
			        		alert($scope.score)
			        		});
			        	}
			        
			    };
		});

app.controller("LoadMCQ" , function($scope,  srvShareData , $http )
		{
				
	$scope.indexToShow = 0;
	$scope.MCQQuestions = srvShareData.getData();
	$scope.score=0 ;
	$scope.change = function(){
		var ans=document.getElementById('answer'+JSON.stringify($scope.indexToShow)).value;
		if (ans==$scope.MCQQuestions[$scope.indexToShow].Answer)
			{
			$scope.score = ($scope.score + 100) ;
			}
		
        $scope.indexToShow = ($scope.indexToShow + 1) ;
        if ($scope.indexToShow==$scope.MCQQuestions.length)
        	{
        	$http.get('http://localhost:8090/SaveScore/'+$scope.score).then(function(response)
        		{
        		var Info = response.data ;
        		alert($scope.score)
        		});
        	}
        
    };
	
	
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


