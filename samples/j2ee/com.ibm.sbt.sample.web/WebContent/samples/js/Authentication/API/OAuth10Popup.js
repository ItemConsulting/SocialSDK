require(["sbt/dom", "sbt/config"], function(dom, config) {
    var endpoint = config.findEndpoint("smartcloud");
    
    config.Properties["loginUi"] = "popup";

    endpoint.authenticate({ forceAuthentication: false }).then(
    	function(response){
            dom.setText("content", "Successfully logged in");    
        },
        function(response){
            dom.setText("content", "Cancelled log in");
        }      
    );
});