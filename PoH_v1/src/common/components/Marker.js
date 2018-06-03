import React from 'react';


const Marker = (props) => {
  const styles = {
      markerStyle: {
          backgroundColor: 'orange',
          width: 20,
          height: 20
      }

  };
  return (
      <div style={styles.markerStyle}></div>
  );
};

export default Marker;
