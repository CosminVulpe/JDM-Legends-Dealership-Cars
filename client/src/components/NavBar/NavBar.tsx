import React from "react";
import styled, {css} from "styled-components/macro";
import {Link} from 'react-router-dom';
import Logo1 from "../../Images/Logo/logo2.jpg";
import { Heading } from '@chakra-ui/react'

const Nav = styled.nav`
  height: 80px;
  display: flex;
  justify-content: space-between;
  padding: 1rem 2rem;
  position: fixed;
  z-index: 100;
  width: 100%;
  background: #070707;
  opacity: 1;
`;

const NavLink = css`
  color: #fff;
  display: flex;
  align-items: center;
  //padding: 0 1rem;
  height: 100%;
  cursor: pointer;
  text-decoration: none;
  font-size: 17px;

  flex: 1 1 auto;
  margin: 10px;
  padding: 20px;
  border: 2px solid #f7f7f7;
  text-align: center;
  text-transform: uppercase;
  position: relative;
  overflow: hidden;
  transition: .3s;

  &:after {
    position: absolute;
    transition: .3s;
    content: '';
    width: 0;
    left: 50%;
    bottom: 0;
    height: 3px;
    background: #f7f7f7;
  }

  &:after {
    height: 120%;
    left: -10%;
    transform: skewX(15deg);
    z-index: -1;
  }

  &:hover {
    cursor: pointer;
    color: ${({primary}: { primary: boolean }) => (primary ? "#4CA8EE" : "#ed2f2f")};

    &:after {
      left: -10%;
      width: 120%;
    }
  }
`;

const Logo = styled(Link)`
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
  text-decoration: none;
  font-size: 25px;

  &:visited {
    color: white;
  }

  h1 {
    color: #873333;
  }

  h1 span {
    color: #ffffff;
  }

`;


const MenuBars = styled.i`
  display: none;

  @media screen and (max-width: 768px) {
    display: block;
    background-size: contain;
    height: 40px;
    width: 40px;
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
    transform: translate(-50%, 25%);
  }
`;

const NavMenu = styled.div`
  display: flex;
  align-items: center;
  margin-right: 50px;

  @media screen and (max-width: 768px) {
    display: none;
  }
`;

const NavMenuLinks = styled(Link)`
  ${NavLink};
`;

const NavBtn = styled.div`
  display: flex;
  align-items: center;
  margin-right: 24px;
  @media screen and (max-width: 768px) {
    display: none;
  }
`;

const ButtonNavBar = styled(Link)`
  white-space: nowrap;
  outline: none;
  border: none;
  min-width: 100px;
  max-width: 200px;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.3s;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 14px 20px;
  font-size: 17px;
  margin: 5px;
  ${NavLink};
`;

const NavBar: React.FC = () => {
    return (
        <Nav>
            <Logo to="/">
                {/*<img src={Logo1}*/}
                {/*     alt="business-logo-jdm-cars"*/}
                {/*     style={{width: "5.6rem"}}/>*/}
                <Heading as="h1"> JDM <span>Legends</span></Heading>
            </Logo>
            <NavMenu>
                <NavMenuLinks to="/about" primary={false}>About</NavMenuLinks>
                <NavMenuLinks to="/all-cars" primary={false}>Cars </NavMenuLinks>
                <NavMenuLinks to="/location" primary={false}>Location </NavMenuLinks>
            </NavMenu>
            <NavBtn>
                <ButtonNavBar to="/login" primary={true}>Login</ButtonNavBar>
                <ButtonNavBar to="/contact-us" primary={true}>Contact Us</ButtonNavBar>
            </NavBtn>
        </Nav>
    );
}

export default NavBar;
