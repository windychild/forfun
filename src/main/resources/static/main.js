


    var word = getUrlParam('word');

    if (word == null) {
        console.log("参数是空")
        gg();
    }else{
        gg(word);
    }

function junpTo(keyword,pageNo){
    if(pageNo == undefined)
        pageNo = 1;
    window.location.href = "/index.html?word=" + keyword+"&pageNo="+pageNo;


}
function gg(word) {
    var pageNo = getUrlParam('pageNo');
    $.get("list?keyWord="+word+"&pageNo="+pageNo,function (data) {
        var main = $(".aaa")

        var dom = main[0];
        dom.innerHTML = "";
        main.after("<div class='aaa'><a href='javascript:;' onclick=junpTo('"+data.keyWord+"',"+data.pre+")>上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick=junpTo('"+data.keyWord+"',"+data.next+")>下一页</a></div>");
        for (let i = 0; i < data.arr.length; i++) {
            //console.log("aaaaa"+data[i])
            var v2 = JSON.parse(data.arr[i]);

            var tag = "<p>tags:&nbsp;&nbsp;&nbsp;&nbsp;"
            for (var j = 0;j< v2.tags.length;j++) {
                var ta = v2.tags[j];

                tag+="<a href='javascript:void()' style='cursor:hand' onClick=junpTo('"+ta+"') >"+ta+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
            }
            tag += "</p>"

            var stars = "<p>stars:&nbsp;&nbsp;&nbsp;&nbsp;"
            for (var j=0; j<v2.stars.length; j++) {
                var star = v2.stars[j];
                stars+="<a  href='javascript:void()' onClick=junpTo('"+star.name+"') >"+star.name+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>"
            }
            stars+="</p>"
            main.after("<div class='aaa'><p><b>"+v2.title+"</b></p>"+tag+stars+"<a target='_blank' href=play.html?fileNames="+Base64.encode(v2.fileName)+"&screenShots="+Base64.encode(v2.screenshotsLocalPath)+"><img src=fff/"+v2.coverLocalPath+"></a></div><br /><br />");
            //main.after("<div class='aaa'><p><b>"+v2.title+"</b></p>"+tag+stars+"<a target='_blank' href=fff/"+v2.fileName[0]+"><img src=fff/"+v2.coverLocalPath+"></a></div><br /><br />");
            //main.after("<div><p><b>"+v2.title+"</b></p>"+tag+stars+"<a target='_blank' href=https://goindex.windychild.com/javclub/"+v2.fileName[0]+"><img src=https://goindex.windychild.com/javclub/"+v2.coverLocalPath+"></a></div><br /><br />");
            //main.after("<div><p><b>"+v2.title+"</b></p>"+tag+stars+"<a target='_blank' href=https://goindex.windychild.com/javclub/"+v2.fileName[0]+"><img src=fff/"+v2.coverLocalPath+"></a></div><br /><br />");
            //console.log(v2.screenshotsLocalPath )
        }
        main.after("<div class='aaa'><b>共查询到"+data.totalCount+"部影片</b></div>")
    })

}






