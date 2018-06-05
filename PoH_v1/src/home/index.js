import React, { Component } from 'react';
import { Container } from 'reactstrap';
import UserRepos from './containers/UserRepos';
import './style.css';
// We can just import Slider or Range to reduce bundle size
// import Slider from 'rc-slider/lib/Slider';
// import Range from 'rc-slider/lib/Range';
import 'rc-slider/assets/index.css';

class Home extends Component {
	render() {
		const Slider = require('rc-slider');
		const createSliderWithTooltip = Slider.createSliderWithTooltip;
		const Range = createSliderWithTooltip(Slider.Range);
		return (
			<div id="home">
				<Container>
					<h2 className="text-center">Home</h2>
					<div>
				    <Range tipProps={{visible:true}} min={-2000} max={2018} defaultValue={[1800, 2018]} tipFormatter={value => {
							if (value>=0){
								return `${value} n.e.`
							} else {
								return `${value*-1} p.n.e.`
							}
						}}/>
				  </div>
					<UserRepos />
				</Container>
			</div>
		);
	}
}

export default Home;
