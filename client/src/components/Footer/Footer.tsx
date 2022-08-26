import React from "react";
import styled from 'styled-components/macro';
import {FaFacebookSquare, FaInstagramSquare, FaTiktok, FaTwitterSquare} from "react-icons/fa";


const FooterContent = styled.footer`
  background: #24262b;
  padding: 70px 0;
`;

const Container = styled.div`
  max-width: 1170px;
  margin: auto;
`;

const Row = styled.div`
  display: flex;
  flex-wrap: wrap;
`;

const FooterCol = styled.div`
  width: 25%;
  padding: 0 15px;

  ul {
    list-style: none;
  }

  @media screen and (max-width: 767px) {
    width: 50%;
    margin-bottom: 30px;
  }

`;

const Title = styled.h4`
  font-size: 18px;
  margin-left: 5px;
  color: #ffffff;
  text-transform: capitalize;
  margin-bottom: 35px;
  font-weight: 500;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    left: 0;
    bottom: -10px;
    background-color: #e91e63;
    height: 2px;
    box-sizing: border-box;
    width: 50px;
  }
`;

const Link = styled.a`
  font-size: 16px;
  text-transform: capitalize;
  color: #ffffff;
  text-decoration: none;
  font-weight: 300;
  display: block;
  transition: all 0.3s ease;
  padding: 2px;

  &:hover {
    color: #ffffff;
    padding-left: 8px;

  }
`;


const Footer: React.FC = () => {
    return (
        <>
            <FooterContent>
                <Container>
                    <Row>
                        <FooterCol>
                            <Title>General Info</Title>
                            <ul>
                                <li>
                                    <Link href="#">About us</Link>
                                    <Link href="#">Our Service</Link>
                                    <Link href="#">Privacy Policy</Link>
                                    <Link href="#">Affiliate Program</Link>
                                </li>
                            </ul>
                        </FooterCol>
                        <FooterCol>
                            <Title>Company</Title>
                            <ul>
                                <li>
                                    <Link href="#">About us</Link>
                                    <Link href="#">Careers</Link>
                                    <Link href="#">Investors</Link>
                                    <Link href="#">Our Founders</Link>
                                </li>
                            </ul>
                        </FooterCol>
                        <FooterCol>
                            <Title>Support</Title>
                            <ul>
                                <li>
                                    <Link href="#">Help Center</Link>
                                    <Link href="#">Cancellation Policy</Link>
                                    <Link href="#">Safety Info Covid-19</Link>
                                    <Link href="#">Support people with disabilities</Link>
                                </li>
                            </ul>
                        </FooterCol>
                        <FooterCol>
                            <Title>Social Media</Title>
                            <ul>
                                <li>
                                    <Link href="https://www.facebook.com/" target="_blank"><FaFacebookSquare
                                        size={25}/></Link>
                                    <Link href="https://about.instagram.com/" target="_blank"><FaInstagramSquare size={25}/></Link>
                                    <Link href="https://twitter.com/" target="_blank"><FaTwitterSquare size={25}/></Link>
                                    <Link href="https://www.tiktok.com/" target="_blank"><FaTiktok size={25}/></Link>
                                </li>
                            </ul>
                        </FooterCol>
                    </Row>
                </Container>
            </FooterContent>
        </>
    );
}

export default Footer;
