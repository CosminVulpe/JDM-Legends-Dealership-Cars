import React from "react";
import styled from "styled-components/macro";
import {FaSearch} from "react-icons/fa";

const Search = styled.div`

  input {
    background-color: white;
    border: 2px solid black;
    border-radius: 2px;
    border-top-right-radius: 0px;
    border-bottom-right-radius: 0px;
    font-size: 18px;
    padding: 15px;
    height: 30px;
    width: 300px;
  }

  input:focus {
    outline: none;
  }

`;

const SearchInput = styled.div`
  padding: 24rem 30rem;
  display: flex;
`;

const SearchResult = styled.div`
  //margin-top: 10px;
  width: 300px;
  height: 200px;
  background-color: white;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  overflow: hidden;
  overflow-y: auto;
  margin: 0 10rem 15rem 30rem;

  &::-webkit-scrollbar {
    display: none;
  }
  
  .dataItem{
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    color: black;
  }
`;

const SearchIcon = styled.div`
  height: 60px;
  width: 50px;
  background-color: white;
  display: grid;
  place-items: center;
`;

const SearchBar: React.FC = () => {
    return (
        <Search>
            <SearchInput>
                <input type="text"
                       size={25}
                       placeholder="Enter car company..."
                />
                <SearchIcon>
                    <FaSearch style={{
                        fontSize: "25px"
                        , marginBottom: "30px"
                    }}/>
                </SearchIcon>
            </SearchInput>
            <SearchResult>

            </SearchResult>
        </Search>
    );
}
export default SearchBar;
