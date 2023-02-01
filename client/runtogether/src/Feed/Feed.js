// import React from 'react';
import './Feed.css';
import {
  ChakraProvider,
  theme,
  Box,
  Card,
} from '@chakra-ui/react';
import Header from '../common/Header';
import Footer from '../common/Footer';
import GoSettingButton from './GoSettingButton';
import ProfileIdPhoto from './ProfileIdPhoto';
import ProfileStatus from './ProfileStatus';
import ProfileFollow from './ProfileFollow';
import ProfileFeed from './ProfileFeed';

const Feed= () => {
  return (
    <ChakraProvider theme={theme} style={{width: "90%"}}>
      <Header></Header>
      <div>   
          <GoSettingButton></GoSettingButton>
          <Box direction={{base: 'row'}} ms='10 %' mt='10px' display='flex'>
            <ProfileIdPhoto></ProfileIdPhoto>
            <Card direction={{base: 'column'}}> 
              <ProfileStatus></ProfileStatus>
              <ProfileFollow></ProfileFollow>
            </Card>
          </Box>
      </div>
      <div>
        <ProfileFeed></ProfileFeed>
      </div>
      <Footer></Footer>
    </ChakraProvider>

  );
}

export default Feed;