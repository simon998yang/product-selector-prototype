$(document).ready(function() {

	$(".range").slider({});
	$(".range").on('slideStop', function() {

		var sliderSelf = this;
		$.ajax({
			url : "rangeFilter",
			type : "get",
			data : {
				range : this.value,
				filterId : this.id
			},
			success : function(response) {
				if (sliderSelf.idsAsString) {
					filter(sliderSelf.id, false, sliderSelf.idsAsString);
				}
				sliderSelf.idsAsString = response;
				filter(sliderSelf.id, true, response);

			},
			error : function(xhr) {
				// Do Something to handle error
			}
		});
	})

});

var allSkuDivs;
var selectedFilters = "";
var visibleDivModelMap = {};

function filter(filterid, selected, skus) {

	allSkuDivs = $(".product-list-item");

	if (selected) {
		if (selectedFilters.length == 0) {
			hideAll();
		}
		selectedFilters += filterid;
		mystr = skus.split(",");
		for (i = 0; i < mystr.length - 1; i++) {
			skuName = mystr[i];
			selectFilter(skuName, filterid, selected);
		}
		$("#productCount").text("" + Object.keys(visibleDivModelMap).length);

	} else {
		selectedFilters = selectedFilters.replace(filterid, "");

		if (selectedFilters.length == 0) {
			showAll();
		} else {
			mystr = skus.split(",");
			for (i = 0; i < mystr.length - 1; i++) {
				skuName = mystr[i];
				unSelectFilter(skuName, filterid);
			}
			$("#productCount").text("" + Object.keys(visibleDivModelMap).length);

		}

	}
}

function selectFilter(skuname, filterid) {

	var map = visibleDivModelMap[skuname];
	if (!map) {
		map = new MapClass();
		visibleDivModelMap[skuname] = map;
	}
	map.put(filterid, filterid);
	var div = document.getElementById(skuName);
	div.style.display = 'block';
}

function unSelectFilter(skuname, filterid) {
	var map = visibleDivModelMap[skuName];

	map.remove(filterid);
	if (map.getCount() == 0) {
		var div = document.getElementById(skuName);
		div.style.display = 'none';
		delete visibleDivModelMap[skuName];
	}
}

function showAll() {
	visibleDivModelMap = {};
	for (i = 0; i < allSkuDivs.length; i++) {
		var div = allSkuDivs[i];
		div.style.display = 'block';
	}

	$("#productCount").text("All");

}

function hideAll() {
	visibleDivModelMap = {};
	for (i = 0; i < allSkuDivs.length; i++) {
		var div = allSkuDivs[i];
		div.style.display = 'none';
	}
}

function MapClass() {
	this.map = new Array();
	this.put = function(key, value) {
		for (var i = 0; i < this.map.length; i++) {
			if (this.map[i].key === key) {
				this.map[i].value = value;
				return;
			}
		}

		this.map[this.map.length] = new struct(key, value);

	};
	this.get = function(key) {
		for (var i = 0; i < this.map.length; i++) {
			if (this.map[i].key === key) {
				return this.map[i].value;
			}
		}

		return null;
	};
	// Delete
	this.remove = function(key) {
		var v;
		for (var i = 0; i < this.map.length; i++) {
			v = this.map.pop();
			if (v.key === key)
				continue;

			this.map.unshift(v);
		}
	};
	this.getCount = function() {
		return this.map.length;
	};
	this.isEmpty = function() {
		return this.map.length <= 0;
	}
}

function struct(key, value) {

	this.key = key;
	this.value = value;

}