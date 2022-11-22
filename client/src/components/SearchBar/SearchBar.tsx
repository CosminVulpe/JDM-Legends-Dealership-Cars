import React, {useState} from "react";
import {BiSearchAlt} from "react-icons/bi";
import {AiOutlineCloseCircle} from "react-icons/ai";
import "./SearchBarStyle.css";
import {Car} from "../IndexPageContent/CarCompany/CarCompany";
import {useNavigate} from "react-router-dom";

interface IProps {
    cars: Car[]
}

const SearchBar: React.FC<IProps> = ({cars}) => {
    const [filteredData, setFilteredData] = useState<any>([]);
    const [wordEntered, setWordEntered] = useState("");
    const navigate = useNavigate();

    const handleFilter = (event: any) => {
        const searchWord = event.target.value;
        setWordEntered(searchWord);

        const newFilter = Object.values(cars)
            .filter((value) => {
                return value.carName
                    .toLowerCase()
                    .includes(searchWord
                        .toLowerCase());
            });

        if (searchWord === "") {
            setFilteredData([]);
        } else {
            setFilteredData(newFilter);
        }
    };

    const clearInput = () => {
        setWordEntered("");
        setFilteredData([]);
    };

    return (
        <>
            <div className="search">
                <div className="searchInputs">
                    <input
                        type="text"
                        placeholder={"Enter a Car Name..."}
                        value={wordEntered}
                        onChange={handleFilter}
                    />
                    <div className="searchIcon">
                        {filteredData.length === 0 ? (
                            <BiSearchAlt/>
                        ) : (
                            <AiOutlineCloseCircle id="clearBtn" onClick={clearInput}/>
                        )}
                    </div>
                </div>
                {filteredData.length !== 0 && (
                    <div className="dataResult">
                        {filteredData
                            .map((value: Car, index: any) => {
                                return (
                                    <a className="dataItem" onClick={() => navigate("/car/" + value.id)}
                                       target="_blank" key={index}>
                                        <p>{value.carName}</p>
                                    </a>
                                );
                            })}
                    </div>
                )}
            </div>
        </>
    )
}
export default SearchBar;
