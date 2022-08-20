import React from "react";

interface Props {
    indexPageVideo?: boolean;
}

const GifAnimation: React.FC<Props> = ({indexPageVideo}) => {
    return (
        <div>
            <video
                src={
                    (indexPageVideo) ? require("../../Video/video3.mp4") :
                        require("../../Video/video4.mp4")}
                style={{
                    width: "148rem"
                    , minHeight: "60rem"
                    , backgroundSize: "cover"
                }}
                muted={true}
                autoPlay={true}
                loop={true}
            />

        </div>
    );
}

export default GifAnimation;
