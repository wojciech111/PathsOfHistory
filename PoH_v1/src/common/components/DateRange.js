import React, {Component} from 'react';
import ReactDOM from 'react-dom';
//import Tooltip from 'rc-tooltip';
//import 'rc-tooltip/assets/bootstrap_white.css';
import Slider from 'rc-slider';
// We can just import Slider or Range to reduce bundle size
// import Slider from 'rc-slider/lib/Slider';
// import Range from 'rc-slider/lib/Range';
import 'rc-slider/assets/index.css';

class DateRange extends Component {
  constructor(props) {
    super(props);
    this.myRef = React.createRef();
  }

  render(){
    //const MARKER_SIZE = 8+props.zoom*2+props.popularitysum/5;
    //console.log("MARKER_SIZE",MARKER_SIZE,"zoom",props.zoom);
    const barWidth=1/202+'%';
    let leftPosition = '0';
    if(this.myRef.current) {
      leftPosition=window.getComputedStyle(ReactDOM.findDOMNode(this.myRef.current).getElementsByClassName('rc-slider-handle-1')[0]).left.match(/\d+/)[0] ;//
    }
    //console.log("aaaaaaaaaaaaa",leftPosition);


    //const Slider = require('rc-slider');
    const createSliderWithTooltip = Slider.createSliderWithTooltip;
    const Range = createSliderWithTooltip(Slider.Range);

    let bars=[];
    let startBasket = Math.floor(this.props.yearsRange[0]/10);
    let endBasket = Math.floor(this.props.yearsRange[1]/10);

    let heightOfGraph=0;

    for(let i=startBasket;i<endBasket;i++){
      if(this.props.dates[i] > 0){
        const barHeight = this.props.dates[i]*3;
        if(barHeight>heightOfGraph){
          heightOfGraph=barHeight;
        }
        const barStyle = {
          width: '0.5%',
          height: barHeight,
          display: 'inline-block',
          position: 'relative',
          backgroundColor: '#aeaeae',
          verticalAlign: 'baseline',
          bottom: 0
        }
        bars.push(<div key={i} style={barStyle} ></div>);

      } else {
        const barStyle = {
          width: '0.5%',
          height: 0,
          display: 'inline-block',
          position: 'relative',
          backgroundColor: '#aeaeae',
          verticalAlign: 'baseline',
          bottom: 0,
        }
        bars.push(<div key={i} style={barStyle} ></div>);
      }
      //console.log(this.props.dates[i], i);
    }
    //console.log(bars);
    const styles = {
        rangeContStyle: {
          position: 'absolute',
          width: '80%',
          bottom: '5%',
          left: '10%',
          backgroundColor: '#fff',
          paddingBottom: 10,
          paddingRight: 10,
          paddingLeft: 10,
        },
        graph: {
          width: '100%',
        	height: heightOfGraph,
        	//backgroundColor: '#800',
          position: 'relative',
          left: parseInt(leftPosition),
          top: 3
        },
        bar: {

        }

    };
    //console.log(this.props.name);
    return (
      <div style={styles.rangeContStyle}>
        <div style={styles.graph}>
            {bars}
        </div>
        <Range ref={this.myRef} style={styles.rangeStyle} onAfterChange={(value) => {this.props.afterRangeChange(value)}}
          min={1} max={2018} defaultValue={this.props.yearsRange} tipProps={{visible:true, placement:'bottom'}}
          tipFormatter={value => `${value} n.e.`}/>
      </div>
    );
  }
};

export default DateRange;
