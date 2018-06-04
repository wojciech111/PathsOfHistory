import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import Slider, { Range } from 'rc-slider';
// We can just import Slider or Range to reduce bundle size
// import Slider from 'rc-slider/lib/Slider';
// import Range from 'rc-slider/lib/Range';
import 'rc-slider/assets/index.css';


import Marker from '../common/components/Marker';

const AnyReactComponent = ({ text }) => <div style={{backgroundColor: 'white'}}>{text}</div>;

class SimpleMap extends Component {

    static defaultProps = {
        center: {
            lat: 59.95,
            lng: 130.33
        },
        fullscreenControl: false,
        zoom: 3
    };
    constructor(props) {
        super(props);
        this.state = {
            events: [],
            categories: [],
            yearsRange: [1800,2018],
            lng:[],//cords x
            lat:[], //cord y
            zoom: 3,
            sidePaneEvent: null,
            sidePaneOpne: false,
            sidePaneArticle: null
        };
    }

    componentDidMount(){
      const pageViewUrl = 'https://wikimedia.org/api/rest_v1';

      //this.getEventsFromApi();

        /*fetch( fullUrl, { headers } ).then( body => body.json() ).then( json => {
            const { head: { vars }, results } = json;
            let events = [];
            let event;
            for ( const result of results.bindings ) {
                event = {};
                for ( const variable of vars ) {
                    event[variable] =result[variable];
                    //console.log( '%s: %o', variable, result[variable] );
                }
                //console.log( '---', event.coordinate_location.value );
                events.push(event);
            }
            //console.log("first events", events);
            return events
        } ).then( events => {
            this.setState({events: events});
            console.log("events",events );
            return events;
        })/*.then(events => {
            events.forEach(event => {
                let querystart = '/metrics/pageviews/per-article/en.wikipedia.org/all-access/all-agents';
                let queryArticleName ='';
                if(event.eventLabel) {
                    queryArticleName ='/'+encodeURIComponent(event.eventLabel.value.trim());
                }
                let queryend = '/monthly/20000101/20180101'
                let pageViewQuery = querystart+queryArticleName+queryend;
                let fullPageViewApicall = pageViewUrl+pageViewQuery;
                let headers = { 'Accept': 'application/json' };

                fetch (fullPageViewApicall, {headers}).then(function(response) {
                    if (response.status >= 200 && response.status < 300) {
                        return Promise.resolve(response)
                    } else {
                        var error = new Error(response.statusText || response.status)
                        error.response = response
                        return Promise.reject(error)
                    }
                }).then( body => body.json() ).then( json => {
                    console.log(json);
                    return null
                }).catch( error => console.log(error));

            });

        })*/



    }
    getEventsFromApi = () => {
      const colorsObj = {
        aqua: "#00ffff",
        azure: "#f0ffff",
        beige: "#f5f5dc",
        black: "#000000",
        blue: "#0000ff",
        brown: "#a52a2a",
        cyan: "#00ffff",
        darkblue: "#00008b",
        darkcyan: "#008b8b",
        darkgrey: "#a9a9a9",
        darkgreen: "#006400",
        darkkhaki: "#bdb76b",
        darkmagenta: "#8b008b",
        darkolivegreen: "#556b2f",
        darkorange: "#ff8c00",
        darkorchid: "#9932cc",
        darkred: "#8b0000",
        darksalmon: "#e9967a",
        darkviolet: "#9400d3",
        fuchsia: "#ff00ff",
        gold: "#ffd700",
        green: "#008000",
        indigo: "#4b0082",
        khaki: "#f0e68c",
        lightblue: "#add8e6",
        lightcyan: "#e0ffff",
        lightgreen: "#90ee90",
        lightgrey: "#d3d3d3",
        lightpink: "#ffb6c1",
        lightyellow: "#ffffe0",
        lime: "#00ff00",
        magenta: "#ff00ff",
        maroon: "#800000",
        navy: "#000080",
        olive: "#808000",
        orange: "#ffa500",
        pink: "#ffc0cb",
        purple: "#800080",
        violet: "#800080",
        red: "#ff0000",
        silver: "#c0c0c0",
        white: "#ffffff",
        yellow: "#ffff00"
      };
      const colors = Object.keys(colorsObj);
      console.log("API start call");
      const {yearsRange,lng,lat} = this.state;
      const endpointUrl = 'http://localhost:3000/',
          fullUrl = endpointUrl +
            `api/events/${yearsRange[0]}-01-01/${yearsRange[1]}-01-01/${lng[0]}/${lng[1]}/${lat[0]}/${lat[1]}` ,
          headers = { 'Accept': 'application/json' };

      //console.log(fullUrl);


      fetch( fullUrl, { headers } ).then( body => body.json() ).then( json => {
        //console.log(json);
        this.setState({events: json});
        console.log("how many events got from API: ",this.state.events/*.length*/);
        return json;
      }).then(events => {
        let flags = {};
        let categories = events.filter((entry) => {
            if (flags[entry.instance_of]) {
                return false;
            }
            flags[entry.instance_of] = true;
            return true;
        }).map((event, i) => ({
          instance_of: event.instance_of, instance_of_label: event.instance_of_label, color: colors[i%42]
        }));
        //console.log("categories",categories);
        //console.log("coloors ",colors);
        this.setState({categories});
        //var unique = events.filter((item, i, ar) =>  ar.indexOf(item) === i; );
      });
    }
    afterRangeChange = (yearsRange) => {
      this.setState({yearsRange}, () => {this.getEventsFromApi();});
      //console.log("yearsRange", yearsRange);

    }
    onMapChange = (value) => {
      console.log(value.zoom);

      const lng = [value.bounds.sw.lng, value.bounds.ne.lng];
      const lat = [value.bounds.sw.lat, value.bounds.ne.lat];
      const zoom = value.zoom;
      //console.log("x:",lng," y:",lat);
      this.setState({lng,lat, zoom},()=>{this.getEventsFromApi();})
    }

    render() {
        //console.log("state.events", this.state.events);

        let markers = this.state.events.map((event, i) => {
            let lng = event.coordinate_x;
            let lat = event.coordinate_y;
            let name = event.placelabel;
            let popularitysum = event.popularitysum;
            let category = this.state.categories.find(x => x.instance_of === event.instance_of);
            let color ='';
            if(category){
              color = category.color;
            }
            //console.log("name",name);
            //console.log("COLOR",category);
            /*console.log("i ", i);
            console.log("lat ", lat);
            console.log("lng", lng);
            console.log( "name", name);
            console.log("------");*/
            return <Marker
                key={i}
                lat={lat}
                lng={lng}
                name={name}
                popularitysum={popularitysum}
                color={color}
                zoom={this.state.zoom}
            />;
        });
        const styles = {
            rangeContStyle: {
                position: 'absolute',
                width: '80%',
                bottom: '10%',
                left: '10%',
                backgroundColor: '#fff',
                padding: 10
            },


        };
        const Slider = require('rc-slider');
    		const createSliderWithTooltip = Slider.createSliderWithTooltip;
    		const Range = createSliderWithTooltip(Slider.Range);
        //console.log("marker", this.state);
        return (
            // Important! Always set the container height explicitly
            <div style={{ height: '100vh', width: '100%' }}>
                <GoogleMapReact
                    defaultCenter={this.props.center}
                    defaultZoom={this.props.zoom}
                    defaultOptions={this.defaultProps}
                    onChange={(value) => {this.onMapChange(value)}}
                >

                    {markers}
                </GoogleMapReact>
                <div style={styles.rangeContStyle}>
                  <Range style={styles.rangeStyle} onAfterChange={(value) => {this.afterRangeChange(value)}}
                    min={-2000} max={2018} defaultValue={this.state.yearsRange} tipProps={{visible:true}}
                    tipFormatter={value => {
    							if (value>=0){
    								return `${value} n.e.`
    							} else {
    								return `${value*-1} p.n.e.`
    							}
    						}}/></div>
            </div>
        );
    }
}

export default SimpleMap;
