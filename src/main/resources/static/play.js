

var fileNames_base64 = getUrlParam("fileNames");
var screenShots_base64 = getUrlParam("screenShots");
var fileNames = Base64.decode(fileNames_base64);
var main = $(".main")
var fileName = fileNames.split(",")
for(var i=0;i<fileName.length;i++){
       main.append("<a href='fff/"+fileName[i]+"'>第"+(i+1)+"集</a>&nbsp;&nbsp");

}
var screenShotsArr = Base64.decode(screenShots_base64).split(",");
for(var i=0;i<screenShotsArr.length;i++){
    main.append("<p><img src='fff/"+screenShotsArr[i]+"' /></p>")

}
console.log(Base64.decode(screenShots_base64))



