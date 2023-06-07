const host = window.location.host;
let file = null;

function goToRegister() {
    location.href = window.location.protocol + "//" + host + "/register";
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

        // 데이터를 서버로 전송
        $.ajax({
            url: '/upload',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(data) {
                alert("성공적으로 상품이 등록되었습니다.");
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
});
