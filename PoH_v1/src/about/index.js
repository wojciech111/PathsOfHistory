import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';

import Marker from '../common/components/Marker';

const AnyReactComponent = ({ text }) => <div style={{backgroundColor: 'white'}}>{text}</div>;

class SimpleMap extends Component {
    static defaultProps = {
        center: {
            lat: 59.95,
            lng: 30.33
        },
        zoom: 3
    };
    constructor(props) {
        super(props);
        this.state = {
            events: []
        };
    }

    componentDidMount(){


        const endpointUrl = 'http://localhost:3000/',
            fullUrl = endpointUrl + 'api/events/100-01-01/2018-01-01/160/180/-180/180' ,
            headers = { 'Accept': 'application/json' };



        const pageViewUrl = 'https://wikimedia.org/api/rest_v1';

        fetch( fullUrl, { headers } ).then( body => body.json() ).then( json => {
          //console.log(json);
          this.setState({events: json});
          console.log(this.state.events);

        });
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


    render() {
        //console.log("state.events", this.state.events);

        let markers = this.state.events.map((event, i) => {
            let lng = event.coordinate_x;
            let lat = event.coordinate_y;
            let name = event.placelabel;


            /*console.log("i ", i);
            console.log("lat ", lat);
            console.log("lng", lng);
            console.log( "name", name);
            console.log("------");*/
            return <Marker
                key={i}
                lat={lat}
                lng={lng}
                text={name}
            />;
        });
        //console.log("marker", this.state);
        return (
            // Important! Always set the container height explicitly
            <div style={{ height: '100vh', width: '100%' }}>
                <GoogleMapReact
                    defaultCenter={this.props.center}
                    defaultZoom={this.props.zoom}
                >

                    {markers}
                </GoogleMapReact>
            </div>
        );
    }
}

export default SimpleMap;
