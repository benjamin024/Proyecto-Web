$(document).ready(function(){
    $("#submit").click(function(){
        var pass = $("#pass").val();
        var user = $("#user").val();

        if( pass == "" || user == "" )
            swal({
                title: "Error",
                type: "warning",
                text: "Ningún campo debe estar vacío."
            });
        else{
    
            $.ajax({
                method : "POST",
                url : "login",
                data : {
                    "pass" : pass,
                    "user" : user
                },
                success : function( response ){
                    var data =JSON.parse(response) ;
                    console.log(data);
                   
                        switch( data ){
                            case 1:
                               
                                location.href = "administrador?user="+user;   
                            break;
                             case 2:
                                 location.href = "profesor?user="+user;
                            break;
                             case 3:
                                 location.href = "alumno?user="+user;
                            break;
                            case 4:
                                swal({
                                    type : "warning",
                                    title : "Error",
                                    text : "Esta cuenta no esta activada"
                                
                                });

                                break;
                            case 5:
                                swal({
                                    type : "warning",
                                    title : "Datos incorrectos",
                                    text : "Favor de revisar la información ingresada"
                                });
                               break;
                              
                        }//switch
                   
                        
                        
                }
            });
        }
    });
});