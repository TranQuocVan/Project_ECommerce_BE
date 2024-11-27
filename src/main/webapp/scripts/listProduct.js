const imgProductListProdcts = document.querySelectorAll('.product-listProdct img');
let clearTimeOuts = [];
let flags = [];

Array.from(imgProductListProdcts).forEach((img, index) => {
    const product = img.parentElement;
    const sizeListProduct = product.getElementsByClassName("size-listProduct")[0];

    img.addEventListener('mouseenter', () => {
        if (flags.includes(index)) {
            clearTimeout(clearTimeOuts[index]);
            flags = flags.filter(flag => flag !== index);
        }

        if (sizeListProduct) {
            sizeListProduct.style.display = 'flex';
            sizeListProduct.style.animation = 'hoverProduct-listProdct 0.8s forwards';
        }
    });

    img.addEventListener('mouseleave', () => {
        if (sizeListProduct) {
            sizeListProduct.style.animation = 'notHoverProduct-listProdct 0.8s forwards';

            clearTimeOuts[index] = setTimeout(() => {
                sizeListProduct.style.display = 'none';
                flags = flags.filter(flag => flag !== index);
            }, 785);

            if (!flags.includes(index)) {
                flags.push(index);
            }
        }
    });

    // Để giữ popup hiển thị khi di chuột vào nó
    sizeListProduct.addEventListener('mouseenter', () => {
        clearTimeout(clearTimeOuts[index]);
        sizeListProduct.style.display = 'flex';
        sizeListProduct.style.animation = 'hoverProduct-listProdct 0.8s forwards';
    });

    sizeListProduct.addEventListener('mouseleave', () => {
        sizeListProduct.style.animation = 'notHoverProduct-listProdct 0.8s forwards';

        clearTimeOuts[index] = setTimeout(() => {
            sizeListProduct.style.display = 'none';
            flags = flags.filter(flag => flag !== index);
        }, 785);
    });




});
