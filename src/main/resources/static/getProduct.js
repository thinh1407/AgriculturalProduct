var url = 'http://localhost:8080/test'
const option = {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
    },
};

fetch(url, option)
    .then(function (res) {
        return res.json();
    })
    .then(function (products) {
        let htmls = products.map(function (product) {
            return `<tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.cate_name}</td>
            <td>${product.quantityProd}</td>
            <td>${product.salePrice}</td>
            <td>${product.cost}</td>
                    </tr>`;
        })
        var html = htmls.join('');
        document.getElementById('product-admin-table').innerHTML = html;
    })