function graph(container, data) {
	var real = data.values[0];
	var step = data.step;
	var d1 = [], start = new Date(data.start).getTime(), options, graph, i, x, o;
	
	for (i = 0; i < data.size; i++) {
		x = start + (i * step);
		d1.push([ x, real[i] ]);
	}
	options = {
		xaxis : {
			mode : 'time',
			timeMode : 'local',
			labelsAngle : 15
		},
		selection : {
			mode : 'x'
		},
		HtmlText : false,
		title : data.titles

	};
	// Draw graph with default options, overwriting with passed options
	function drawGraph(opts) {

		// Clone the options, so the 'options' variable always keeps intact.
		o = Flotr._.extend(Flotr._.clone(options), opts || {});

		if (opts != null && opts.xaxis != null) {
			o.title = " From " + formatDate(new Date(opts.xaxis.min)) + " To "
					+ formatDate(new Date(opts.xaxis.max));
		} else {
		}
		// Return a new graph.
		return Flotr.draw(container, [ d1 ], o);
	}

	graph = drawGraph();

	Flotr.EventAdapter.observe(container, 'flotr:select', function(area) {
		// Draw selected area
		graph = drawGraph({
			xaxis : {
				min : area.x1,
				max : area.x2,
				mode : 'time',
				timeMode : 'local',
				labelsAngle : 15
			},
			yaxis : {
				min : area.y1,
				max : area.y2
			},
			HtmlText : true
		});
	});
	// When graph is clicked, draw the graph with default area.
	Flotr.EventAdapter.observe(container, 'flotr:click', function() {
		graph = drawGraph();
	});
}

