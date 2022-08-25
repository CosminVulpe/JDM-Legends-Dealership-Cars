import React from "react";
import styled, {keyframes} from "styled-components/macro";

interface Props {
    indexPageVideo?: boolean;
}

const Rotate = keyframes`
  0% {
    border-color: #fff;
    transform: translate(0, 0);
  }
  20% {
    border-color: #fff;
    transform: translate(15px, 15px);
  }
  20.1%, 100% {
    border-color: #ffd64a;
  }
`;

const Body = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

const AnimationArrays = styled.div`
  position: relative;
  width: 50px;
  height: 50px;
  transform: rotate(45deg);
  margin-top: -15rem;
  margin-right: 6rem;

  span {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    border: none;
    border-bottom: 3px solid #fff;
    border-right: 3px solid #fff;
    animation: ${Rotate} 1s linear infinite;

    &:nth-child(1) {
      top: -30px;
      left: -30px;
      animation-delay: 0s;
    }

    &:nth-child(2) {
      top: -15px;
      left: -15px;
      animation-delay: 0.2s;
    }

    &:nth-child(3) {
      top: 0;
      left: 0;
      animation-delay: 0.4s;
    }

    &:nth-child(4) {
      top: 15px;
      left: 15px;
      animation-delay: 0.6s;
    }

    &:nth-child(5) {
      top: 30px;
      left: 30px;
      animation-delay: 0.8s;
    }
  }
`;


const GifAnimation: React.FC<Props> = ({indexPageVideo}) => {

    const handleClick = (): void => {
        let offSetTop = document.getElementById("content-index")!.offsetTop;
        window.scrollTo({
            top: offSetTop - 100,
            behavior: "smooth"
        });
    }

    return (
        <div>
            <video
                src={
                    (indexPageVideo) ? require("../../Video/video3.mp4") :
                        require("../../Video/video4.mp4")}
                style={{
                    width: "120rem"
                    , minHeight: "62rem"
                    , objectFit: "cover"
                }}
                muted={true}
                autoPlay={true}
                loop={true}
            />
            <Body onClick={handleClick}>
                <AnimationArrays>
                    {[...Array(5)].map((nr, index) =>
                        <span key={index}></span>
                    )}
                </AnimationArrays>
            </Body>
        </div>
    );
}

export default GifAnimation;
