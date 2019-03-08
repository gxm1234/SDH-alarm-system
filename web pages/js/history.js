	var width  = 600;	//SVG绘制区域的宽度
	var height = 400;	//SVG绘制区域的高度
			
	var svg = d3.select("#duizhan")			//选择<body>
				.append("svg")			//在<body>中添加<svg>
				.attr("width", width)	//设定<svg>的宽度属性
				.attr("height", height);//设定<svg>的高度属性
	
	//1. 确定初始数据
	/*
	var dataset = [
        { name: "HWarning" , 
		  content: [{ time:Time[0], value: parseInt(HWarning[0]) },
					{ time:Time[1], value: parseInt(HWarning[1]) },
					{ time:Time[2], value: parseInt(HWarning[2]) },
					{ time:Time[3], value: parseInt(HWarning[3]) }] },
		{ name: "MWarning" , 
		  content: [{ time:Time[0], value: parseInt(MWarning[0]) },
					{ time:Time[1], value: parseInt(MWarning[1]) },
					{ time:Time[2], value: parseInt(MWarning[2]) },
					{ time:Time[3], value: parseInt(MWarning[3]) }] },
		{ name: "NWarning" , 
		  content: [{ time:Time[0], value: parseInt(NWarning[0]) },
					{ time:Time[1], value: parseInt(NWarning[1]) },
					{ time:Time[2], value: parseInt(NWarning[2]) },
					{ time:Time[3], value: parseInt(NWarning[3]) }] },
					
		{ name: "IWarning" , 
		  content: [{ time:Time[0], value: parseInt(IWarning[0]) },
					{ time:Time[1], value: parseInt(IWarning[1]) },
					{ time:Time[2], value: parseInt(IWarning[2]) },
					{ time:Time[3], value: parseInt(IWarning[3]) }] }
    ];
	*/
	
		
		var content1=[];
		var content2=[];
		var content3=[];
		var content4=[];
		
		for(var i=0;i<Time.length;i++)
		{
			content1.push({ time:Da[i]+" "+Time[i], value: parseInt(CAlarm[i])});
			content2.push({ time:Da[i]+" "+Time[i], value: parseInt(MAlarm[i])});
			content3.push({ time:Da[i]+" "+Time[i], value: parseInt(NAlarm[i])});
			content4.push({ time:Da[i]+" "+Time[i], value: parseInt(WAlarm[i])});
		}
		
		
		var dataset = [
        { name: "CAlarm" , 
		  content: content1 },
		{ name: "MAlarm" , 
		  content: content2 },
		{ name: "NAlarm" , 
		  content: content3 },	
		{ name: "WAlarm" , 
		  content: content4 }
		];
	
	
	//2. 转换数据
	var stack = d3.layout.stack()
					.values(function(d){ return d.content; })
					.x(function(d){ return d.time; })
					.y(function(d){ return d.value; });

	var data = stack(dataset);

	console.log(data);
		
		
	//3. 绘制

	//外边框
	var padding = { left:50, right:100, top:30, bottom:30 };
	
	//创建x轴比例尺
	var xRangeWidth = width - padding.left - padding.right;
		
	var xScale = d3.scale.ordinal()
					.domain(data[0].content.map(function(d){ return d.time; }))
					.rangeBands([0, xRangeWidth],0.3);

	//创建y轴比例尺
	
	//最大利润（定义域的最大值）
	var maxProfit = d3.max(data[data.length-1].content, function(d){ 
							return d.y0 + d.y; 
					});
	
	//最大高度（值域的最大值）
	var yRangeWidth = height - padding.top - padding.bottom;
	
	var yScale = d3.scale.linear()
					.domain([0, maxProfit])		//定义域
					.range([0, yRangeWidth]);	//值域
	
	
	//颜色比例尺
	var color = d3.scale.category10();
	
	//添加分组元素
	var groups = svg.selectAll("g")
					.data(data)
					.enter()
					.append("g")
					.style("fill",function(d,i){ return color(i); });
		
	//添加矩形
	var rects = groups.selectAll("rect")
					.data(function(d){ return d.content; })
					.enter()
					.append("rect")
					.attr("x",function(d){ return xScale(d.time); })
					.attr("y",function(d){ return yRangeWidth - yScale( d.y0 + d.y ); })
					.attr("width",function(d){ return xScale.rangeBand(); })
					.attr("height",function(d){ return yScale(d.y); })
					.attr("transform","translate(" + padding.left + "," + padding.top + ")");
	
	//添加坐标轴
	var xAxis = d3.svg.axis()
				.scale(xScale)
				.orient("bottom");
		
	yScale.range([yRangeWidth, 0]);
	
	var yAxis = d3.svg.axis()
					.scale(yScale)
					.orient("left");
					
	svg.append("g")
			.attr("class","axis")
			.attr("transform","translate(" + padding.left + "," + (height - padding.bottom) +  ")")
			.call(xAxis);
				
	svg.append("g")
			.attr("class","axis")
			.attr("transform","translate(" + padding.left + "," + (height - padding.bottom - yRangeWidth) +  ")")
			.call(yAxis); 
			
	//添加分组标签
	var labHeight = 50;
	var labRadius = 10;
	
	var labelCircle = groups.append("circle")
						.attr("cx",function(d){ return width - padding.right*0.98; })
						.attr("cy",function(d,i){ return padding.top * 2 + labHeight * i; })
						.attr("r",labRadius);
					
	var labelText = groups.append("text")
						.attr("x",function(d){ return width - padding.right*0.8; })
						.attr("y",function(d,i){ return padding.top * 2 + labHeight * i; })
						.attr("dy",labRadius/2)
						.text(function(d){ return d.name; });