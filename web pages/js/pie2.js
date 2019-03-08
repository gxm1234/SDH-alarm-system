		var width = 200;
		var height = 200;
		var dataset = [parseFloat(Alarm[5]),parseFloat(Alarm[6]),parseFloat(Alarm[7]),parseFloat(Alarm[8]),parseFloat(Alarm[9]),parseFloat(Alarm[10])];
		
		var svg = d3.select("#piecont2")
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
		
		//var color = d3.scale.category10();
		
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
				else if(i==3)
				{
					color="#FFEFD5";
				}
				else if(i==4)
				{
					color="#9400D3";
				}
				else
				{
					color="	#8B0000";
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