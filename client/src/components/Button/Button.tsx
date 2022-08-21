import styled from 'styled-components';
import {Link} from 'react-router-dom';
import React from "react";

interface Props {
    buttonText?: string;
}


export const ButtonStyle = styled(Link)`
  position: relative;
  display: inline-block;
  cursor: pointer;
  outline: none;
  border: 0;
  vertical-align: middle;
  text-decoration: none;
  background: transparent;
  padding: 0;
  font-size: inherit;
  font-family: inherit;

  width: 12rem;
  height: auto;

  .circle {
  @include transition(all, 0.45 s, cubic-bezier(0.65, 0, .076, 1));
    position: relative;
    display: block;
    margin: 0;
    width: 3rem;
    height: 3rem;
    background: #282936;
    border-radius: 1.625rem;
  }

  .circle .icon {
  @include transition(all, 0.45 s, cubic-bezier(0.65, 0, .076, 1));
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto;
    background: #fff;
  }

  .circle .icon.arrow {
  @include transition(all, 0.45 s, cubic-bezier(0.65, 0, .076, 1));
    left: 0.625rem;
    width: 1.125rem;
    height: 0.125rem;
    background: none;

    &::before {
      position: absolute;
      content: '';
      top: -0.25rem;
      right: 0.0625rem;
      width: 0.625rem;
      height: 0.625rem;
      border-top: 0.125rem solid #fff;
      border-right: 0.125rem solid #fff;
      transform: rotate(45deg);
    }
  }

  .button-text {
  @include transition(all, 0.45 s, cubic-bezier(0.65, 0, .076, 1));
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    padding: 0.75rem 0;
    margin: 0 0 0 1.85rem;
    color: #282936;
    font-weight: 700;
    line-height: 1.6;
    text-align: center;
    text-transform: uppercase;
  }

  &:hover {
    .circle {
      width: 100%;

      .icon.arrow {
        background: #fff;
        transform: translate(1rem, 0);
      }
    }

    .button-text {
      color: #fff;
    }
  }
`;

const Button: React.FC<Props> = ({buttonText}) => {
    return (
        <ButtonStyle to="/">
                <span className="circle" aria-hidden="true">
                    <span className="icon arrow"></span>
                </span>
            <span className="button-text">{buttonText}</span>
        </ButtonStyle>
    )
};

export default Button;
