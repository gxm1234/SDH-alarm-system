		var width = 200;
		var height = 200;
		var dataset = [parseFloat(Alarm[1]),parseFloat(Alarm[2]),parseFloat(Alarm[3]),parseFloat(Alarm[4])];
		
		
		var svg = d3.select("#piecont1")
					.append("svg")
					.attr("width", width)
					.attr("height", height);
		
		var pie = d3.layout.pie();

		var piedata = pie(dataset);
		
		var outerRadius = 75;	//外半径
		var innerRadius = 0;	//内半径，为0则中间没有空白

		var arc = d3.svg.arc()	//弧生成器
					.innerRadius(innerRadius)	//设置内半径
					.outerRadius(outerRadius);	//设置外半径
		
		
		var arcs = svg.selectAll("g")
					  .data(piedata)
					  .enter()
					  .append("g")
					  .attr("transform","translate("+ (width/2) +","+ (width/2) +")");
					  
		arcs.append("path")
			.attr("fill",function(d,i){
			
				var color="#FF0000";
				
				if(i==0)
				{
					color="#FF0000";
				}
				else if(i==1)
				{
					color="#FF34B3";
				}
				else if(i==2)
				{
					color="#FFD700";
				}
				else
				{
					color="#FFEFD5";
				}
			
				return color;
			})
			.attr("d",function(d){
				return arc(d);
			});
		
		arcs.append("text")
			.attr("transform",function(d){
				return "translate(" + arc.centroid(d) + ")";
			})
			.attr("text-anchor","middle")
			.text(function(d){
				if(d.data!=0)
				{
					return d.data;
				}
			});
		
		console.log(dataset);
		console.log(piedata);