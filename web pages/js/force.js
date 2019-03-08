		/*var nodes = [ {name: save[0], st: state[0]}, {name: save[1], st: state[1]},
					  {name: save[2], st: state[2]}, {name: save[3], st: state[3]},
					  {name: save[4], st: state[4]}, {name: save[5], st: state[5]},
					  {name: save[6], st: state[6]} ];*/
					  
		var nodes = [];
		
		for(var i=0;i<save.length;i++)
		{
			nodes.push({name: save[i], st: state[i]});
		}
					 
		/*var edges = [  { source : 0  , target: 1 } , { source : 0  , target: 2 } ,
					   { source : 0  , target: 3 } , { source : 1  , target: 4 } ,
					   { source : 1  , target: 5 } , { source : 1  , target: 6 }  ];*/	
		 var edges = [  { source : 0  , target: 1 } , { source : 0  , target: 2 }   ];
		
		var width = 750;
		var height = 400;
		
		
		var svg = d3.select("#force")
					.append("svg")
					.attr("width",width)
					.attr("height",height);
		
		var force = d3.layout.force()
				.nodes(nodes)		
				.links(edges)		
				.size([width,height])	
				.linkDistance(150)	
				.charge(-400);	

		force.start();	//开始作用

		console.log(nodes);
		console.log(edges);
		
		//添加连线		
		var svg_edges = svg.selectAll("line")
							.data(edges)
							.enter()
							.append("line")
							.style("stroke","#ccc")
							.style("stroke-width",1);
		
		//var color = d3.scale.category20();
		
				
		//添加节点			
		var svg_nodes = svg.selectAll("circle")
							.data(nodes)
							.enter()
							.append("circle")
							.attr("r",20)
							.style("fill",function(d){

								var color = "#FF0000";
								
								if(d.st==1)
								{
									color="#FF0000";
								}
								else if(d.st==2)
								{
									color="#FF34B3";
								}
								else if(d.st==3)
								{
									color="#FFD700";
								}
								else
								{
									color="#FFEFD5";
								}

								return color;
							})
							
							.on("dblclick",function(d){
								window.location.href="http://localhost:8080/SDH/detail.jsp?Device="+d.name;
								
							})
							.call(force.drag);	//使得节点能够拖动

		//添加描述节点的文字
		var svg_texts = svg.selectAll("text")
							.data(nodes)
							.enter()
							.append("text")
							.style("fill", "black")
							.attr("dx", 20)
							.attr("dy", 8)
							.text(function(d){
								return d.name;
							});
					

		force.on("tick", function(){	//对于每一个时间间隔
		
			 //更新连线坐标
			 svg_edges.attr("x1",function(d){ return d.source.x; })
			 		.attr("y1",function(d){ return d.source.y; })
			 		.attr("x2",function(d){ return d.target.x; })
			 		.attr("y2",function(d){ return d.target.y; });
			 
			 //更新节点坐标
			 svg_nodes.attr("cx",function(d){ return d.x; })
			 		.attr("cy",function(d){ return d.y; });

			 //更新文字坐标
			 svg_texts.attr("x", function(d){ return d.x; })
			 	.attr("y", function(d){ return d.y; });
		});