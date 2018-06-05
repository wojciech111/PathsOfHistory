import React from 'react';
//import Tooltip from 'rc-tooltip';
//import 'rc-tooltip/assets/bootstrap_white.css';


const Legend = (props) => {
  //const MARKER_SIZE = 8+props.zoom*2+props.popularitysum/5;
  //console.log("MARKER_SIZE",MARKER_SIZE,"zoom",props.zoom);
  const styles = {
    drawerStyle: {
      position: 'absolute',
      width: '12%',
      height: '75%',
      top: '8%',
      right: '1%',
    },
    header: {
      //height: 30,
      fontSize: '0.9em',
      textAlign: 'center'
    },
    scrollerStyle: {
      height: '90%',

      //padding: 10,
      //display: 'flex',
      //flexDirection: 'column',
      //flexWrap: 'nowrap',
      //margin: '0 0 3em 0',
      //padding: 0
      //overflow: 'hidden', // Or flex might break
      overflow: 'auto', // Or flex might break

    },
    row: {
      backgroundColor: '#f5f5f5',
      //flexGrow: 1,
      display: 'inline',
      float: 'left',
      borderRadius: '20%',
      margin:5,
      width: '100%'
    },
    descriptionStyle: {
      margin: 2,

      //float: 'left',
      fontSize: 14,
      //boxSizing: 'border-box',
      //display: 'inline-block',
      //width: '80%', // Default to full width
      //padding: '0.8em 1.2em',
    }


  };
  let categoriesLegends = props.categories.map((category, i) => {
    const dotStyle =  {
        backgroundColor: category.color,
        postion: 'absolute',
        left: -6,
        top: -6,
        width: 12,
        height: 12,
        borderRadius: '50%',
        display: 'inline-block',
        marginTop: 6,
        marginBottom: 6,
        marginLeft: 2,
        marginRight: 6,
        float: 'left',

        //padding: '0.8em 1.2em',
    }
    return <span key={i} style={styles.row}><div style={dotStyle}></div><p style={styles.descriptionStyle}>{category.instance_of_label}</p></span>
  });
  //console.log(props.name);
  return (
      <div style={styles.drawerStyle}>
        <div style={styles.header}>
          <div>Showing <b>{props.numberOfEvents}</b> events</div>
          <div>Size of dot based on <b>wiki page popularity</b></div>
        </div>
        <div style={styles.scrollerStyle}>{categoriesLegends}</div>
      </div>
  );
};

export default Legend;
