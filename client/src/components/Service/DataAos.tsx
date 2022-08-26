import AOS from 'aos';
import "aos/dist/aos.css";

 export const DataAos = () :void=> {
    AOS.init({
        duration: 1500
    });
    AOS.refresh();
}

