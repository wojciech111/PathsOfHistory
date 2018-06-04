import React from 'react';
import Tooltip from 'rc-tooltip';
import 'rc-tooltip/assets/bootstrap_white.css';


const Marker = (props) => {
  const MARKER_SIZE = 8+props.zoom*2+props.popularitysum/5;
  //console.log("MARKER_SIZE",MARKER_SIZE,"zoom",props.zoom);
  const styles = {
      markerStyle: {
          backgroundColor: props.color,
          position: 'absolute',
          width: MARKER_SIZE,
          height: MARKER_SIZE,
          left: -MARKER_SIZE / 2,
          top: -MARKER_SIZE / 2,
          borderRadius: '50%',
      }

  };
  //console.log(props.name);
  return (
      <Tooltip overlay={<span>{props.name}</span>} placement='top'><div style={styles.markerStyle}></div></Tooltip>
  );
};

export default Marker;
