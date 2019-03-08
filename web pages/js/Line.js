var width=600;  
var height=400;  

var content1=[];
var content2=[];
var content3=[];
var content4=[];
		
		


var xMarks=[];

		
for(var i=0;i<Time.length;i++)
{
	content1.push([i,parseInt(CAlarm[i])]);
	content2.push([i,parseInt(MAlarm[i])]);
	content3.push([i,parseInt(NAlarm[i])]);
	content4.push([i,parseInt(WAlarm[i])]);

	xMarks.push(Da[i]+" "+Time[i]);
}
		
		
		var dataset = [
        { country: "CAlarm" , 
		  gdp: content1 },
		{ country: "MAlarm" , 
		  gdp: content2 },
		{ country: "NAlarm" , 
		  gdp: content3 },	
		{ country: "WAlarm" , 
		  gdp: content4 }
		];
		


var padding={top:25, right:75, bottom: 25, left:75};  
var gdpmax=0;  
for(var i=0;i<dataset.length;i++){  
    var currGdp=d3.max(dataset[i].gdp,function(d){  
        return d[1];  
    });  
    if(currGdp>gdpmax)  
        gdpmax=currGdp;  
}  
console.log(gdpmax);  
  
var xScale=d3.scale.linear()  
            .domain([0,Time.length-1])  
            .range([0,width-padding.left-padding.right]);  
  
var yScale=d3.scale.linear()  
            .domain([0,gdpmax*1.1])  
            .range([height-padding.bottom-padding.top,0]);  
  
var linePath=d3.svg.line()//创建一个直线生成器  
                .x(function(d){  
                    return xScale(d[0]);  
                })  
                .y(function(d){  
                    return yScale(d[1]);  
                })  
                .interpolate("basis")//插值模式  
                ;  
  
//定义两个颜色  
/*var colors=[d3.rgb(0,0,255),d3.rgb(0,255,0),d3.rgb(255,0,0),d3.rgb(0,0,0)];  */

var colors = d3.scale.category10();
  
var svg=d3.select("#line")  
                .append("svg")  
                .attr("width",width)  
                .attr("height",height);  
  
svg.selectAll("path")  
    .data(dataset)  
    .enter()  
    .append("path")  
    .attr("transform","translate("+padding.left+","+padding.top+")")  
    .attr("d",function(d){  
        return linePath(d.gdp);  
        //返回线段生成器得到的路径  
    })  
    .attr("fill","none")  
    .attr("stroke-width",3)  
    .attr("stroke",function(d,i){  
        return colors(i);  
    });  
  
var xAxis=d3.svg.axis()  
            .scale(xScale)  
            .ticks(5)  
            .tickFormat(d3.format("d"))  
            .orient("bottom");  
  
var yAxis=d3.svg.axis()  
            .scale(yScale)  
            .orient("left");  
  
//添加一个g用于放x轴  
svg.append("g")  
    .attr("class","axis")  
    .attr("transform","translate("+padding.left+","+(height-padding.top)+")")  
    .call(xAxis)
	.selectAll("text")

    .text(function(d){return xMarks[d];});

  
svg.append("g")  
    .attr("class","axis")  
    .attr("transform","translate("+padding.left+","+padding.top+")")  
    .call(yAxis);  


	