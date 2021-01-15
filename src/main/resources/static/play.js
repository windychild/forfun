

var fileNames_base64 = getUrlParam("fileNames");

var fileNames = Base64.decode(fileNames_base64);
var main = $(".main")
if(fileNames.indexOf(",")!=-1)
{
    var fileName = fileNames.split(",")
    for(var i=0;i<fileName.length;i++){
           main.append("<a href='fff/"+fileName[i]+"'>第"+(i+1)+"集</a>&nbsp;&nbsp");

    }



}else{
    window.location.href = "fff/"+fileNames
    //main.append("<a href='fff/"+fileNames+"'>第一集</a>");
}
console.log(fileName)



