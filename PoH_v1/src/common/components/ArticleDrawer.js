import React from 'react';
//import Tooltip from 'rc-tooltip';
//import 'rc-tooltip/assets/bootstrap_white.css';


const ArticleDrawer = (props) => {
  //const MARKER_SIZE = 8+props.zoom*2+props.popularitysum/5;
  //console.log("MARKER_SIZE",MARKER_SIZE,"zoom",props.zoom);
  const styles = {
      drawerStyle: {
        position: 'absolute',
        width: '40%',
        height: '80%',
        top: '2%',
        left: '1%',
        backgroundColor: '#f5f5f5',
        padding: 20,
        overflow: 'auto'
      },
      article: {
        textAlign: 'justify'
      },
      title: {
        textAlign: 'center'
      },
      image: {
        width: '100%'
      }

  };
  console.log(props.image);
  return (
      <div style={styles.drawerStyle}>
        <div onClick={()=>props.close()}>Close</div>
        <h2 style={styles.title}>{props.title}</h2>
        <img style={styles.image} src={props.image}/>
        <div style={styles.article} dangerouslySetInnerHTML={{ __html: props.children }} />

      </div>
  );
};

export default ArticleDrawer;
