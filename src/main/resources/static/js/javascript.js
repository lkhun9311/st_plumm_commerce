const host = window.location.host;
let serverUrl = undefined;
let file = undefined;

function goToRegister() {
    if (host == "albdev.lkhun.store") {
        location.href = window.location.protocol + "//" + host + "/register";
    } else if (host == "alb.lkhun.store") {
        location.href = window.location.protocol + "//" + host + "/register";
    } else if (host == "localhost:8080") {
        location.href = window.location.protocol + "//" + host + "/register";
    } else {
        location.href = "https://lkhun.store/registerProduct.html";
    }
    // location.href = window.location.protocol + "//" + host + "/register";
};

function goToMain() {
    location.href = window.location.protocol + "//" + host;
}

function uploadFile(inputFile) {
    file = inputFile.files[0];	//선택된 파일 가져오기
};

$().ready(function() {
    $('#upload-form').on('submit', function(e) {
        e.preventDefault();  // 폼이 정상적으로 제출되는 것을 방지
        // console.log(file);

        const productName = $('#productName').val();  // 상품 이름을 가져옵니다.
        const productPrice = $('#productPrice').val();  // 상품 가격을 가져옵니다.
        const productImage = file;  // 이미지 파일을 가져옵니다.

        const data = {
            productName: productName,
            productPrice: productPrice
        };
        // console.log(data);

        const formData = new FormData();  // 새로운 FormData 객체를 생성
        formData.append("requestDto", new Blob([JSON.stringify(data)] , {type: "application/json"}));
        formData.append("imageFile", productImage);
        // // console.log(formData);
        // for (const pair of formData.entries()) { console.log('{"' + pair[0] + '"' + ': ' + '' + pair[1] + '}'); }

        if (host == "albdev.lkhun.store") {
            serverUrl = "/uploadDev";
        } else if (host == "alb.lkhun.store") {
            serverUrl = "/uploadProd";
        } else if (host == "localhost:8080") {
            serverUrl = "http://localhost:8080/uploadProd";
        }
        else {
            serverUrl = "https://alb.lkhun.store/uploadProd";
        }
        console.log(serverUrl);

        // 데이터를 서버로 전송
        $.ajax({
            url: serverUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                alert("상품이 성공적으로 등록되었습니다.");
                // console.log(data);
                // alert(JSON.stringify(data));
                location.href = window.location.protocol + "//" + host;
            },
            error: function(e) {
                // console.log(e);
                alert("상품 등록에 실패했습니다.");
                location.href = window.location.protocol + "//" + host;
            }
        });
    });

    $("#register").click(function() {
        const imageFileNameArr = $("#productImage").val().split(String.fromCharCode(92));
        const imageFileName = imageFileNameArr[imageFileNameArr.length - 1];

        if($("#productName").val().length == 0) {
            alert("Please enter your product name.");
            $("#productName").focus();
            return false;
        }
        if($("#productPrice").val().length == 0) {
            alert("Please enter your product price.");
            $("#productPrice").focus();
            return false;
        }
        if(imageFileName.length == 0) {
            alert("Please enter your product image.");
            return false;
        }
    });

    if (host == "albdev.lkhun.store") {
        serverUrl = "/readAllDev";
    } else if (host == "alb.lkhun.store") {
        serverUrl = "/readAllProd";
    } else if (host == "localhost:8080") {
        serverUrl = "http://localhost:8080/readAllDev";
    }
    else {
        serverUrl = "https://alb.lkhun.store/readAllProd";
    }
    // console.log(serverUrl);

    // 메인 화면에 상품 목록 카드 형식으로 보여주기
    $.ajax(
        {
        // url: 'https://alb.lkhun.store/readAll',
        url: serverUrl,
        method: 'GET',
        success: function(products) {
            // alert("상품 불러오기에 성공했습니다.");
            for (let i = 0; i < products.data.length; i++) {
                const product = products.data[i];
                // console.log(product.productName);
                // console.log(product.productPrice);
                // console.log(product.imageFileUrl);
                const productCardHTML = `<div class="col mb-5">
                                        <div class="card h-100">
                                            <!-- Product image-->
                                            <img class="card-img-top" src="${product.imageFileUrl}" alt="..." />
                                            <!-- Product details-->
                                            <div class="card-body p-4">
                                                <div class="text-center">
                                                    <!-- Product name-->
                                                    <h5 class="fw-bolder">${product.productName}</h5>
                                                    <!-- Product price-->
                                                    ₩${product.productPrice}
                                                </div>
                                            </div>
                                            <!-- Product actions-->
                                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                                <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">Add to cart</a></div>
                                            </div>
                                        </div>
                                    </div>`;
                $('#product-list').append(productCardHTML);
            }
        },
        error: function(e) {
            // console.log(e);
            alert("상품 불러오기에 실패했습니다.");
            location.href = window.location.protocol + "//" + host;
        }
    });
});
