[실전! 스프링부트와 JPA 활용1]

- 목표 화면

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0e651e82-a3e8-46f0-be47-f45b51b6366b/Untitled.png)

### 기능 목록

- 회원 기능
  - 회원 등록
  - 회원 조회
- 상품 기능
  - 상품 등록
  - 상품 수정
  - 상품 조회
- 주문 기능
  - 상품 주문
  - 주문 내역 조회
  - 주문 취소
- 기타 요구사항
  - 상품은 재고 관리가 필요하다.
  - 상품의 종류는 도서, 음반, 영화가 있다.
  - 상품을 카테고리로 구분할 수 있다

### 설계

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8ef5cfca-cb57-4d1a-871a-557c972c17bd/Untitled.png)

- **회원, 주문, 상품의 관계**: 회원은 여러 상품을 주문할 수 있다. 그리고 한 번 주문할 때 여러 상품을 선택할 수 있으므로 주문과 상품은 다대다 관계다. 하지만 이런 다대다 관계는 관계형 데이터베이스는 물론이고 엔티티에서도 거의 사용하지 않는다. 따라서 그림처럼 주문상품이라는 엔티티를 추가해서 다대다 관계를 일대다, 다대일 관계로 풀어냈다.
- **상품 분류**: 상품은 도서, 음반, 영화로 구분되는데 상품이라는 공통 속성을 사용하므로 상속 구조로 표현했다.

- 다 대 다 사용 X
  - N:N은 기본편에서 설명했듯이 사용하지 않는 것이 좋다

### 회원 엔티티 분석

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ce0c6f7d-5bac-4734-bc46-dd53c643fb51/Untitled.png)

- **회원(Member)**: 이름과 임베디드 타입인 주소( Address ), 그리고 주문( orders ) 리스트를 가진다.
- **주문(Order)**: 한 번 주문시 여러 상품을 주문할 수 있으므로 주문과 주문상품( OrderItem )은 일대다 관계다. 주문은 상품을 주문한 회원과 배송 정보, 주문 날짜, 주문 상태( status )를 가지고 있다. 주문
  상태는 열거형을 사용했는데 주문( ORDER ), 취소( CANCEL )을 표현할 수 있다.
- **주문상품(OrderItem)**: 주문한 상품 정보와 주문 금액( orderPrice ), 주문 수량( count ) 정보를 가지고 있다. (보통 OrderLine , LineItem 으로 많이 표현한다.)
- **상품(Item)**: 이름, 가격, 재고수량( stockQuantity )을 가지고 있다. 상품을 주문하면 재고수량이 줄어든다. 상품의 종류로는 도서, 음반, 영화가 있는데 각각은 사용하는 속성이 조금씩 다르다.
- **배송(Delivery)**: 주문시 하나의 배송 정보를 생성한다. 주문과 배송은 일대일 관계다.
- **카테고리(Category)**: 상품과 다대다 관계를 맺는다. parent , child 로 부모, 자식 카테고리를 연결한다.
- **주소(Address)**: 값 타입(임베디드 타입)이다. 회원과 배송(Delivery)에서 사용한다.

### 회원 테이블 분석

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/408cb489-e51b-4924-a883-9c92ef68d4f8/Untitled.png)

- **MEMBER**: 회원 엔티티의 Address 임베디드 타입 정보가 회원 테이블에 그대로 들어갔다. 이것은 DELIVERY 테이블도 마찬가지다.
- **ITEM**: 앨범, 도서, 영화 타입을 통합해서 하나의 테이블로 만들었다. DTYPE 컬럼으로 타입을 구분한다.

### 연관 관계 매핑 분석

- **회원과 주문**: 일대다 , 다대일의 양방향 관계다. 따라서 연관관계의 주인을 정해야 하는데, 외래 키가 있는 주문을 연관관계의 주인으로 정하는 것이 좋다. 그러므로 Order.member 를 ORDERS.MEMBER_ID 외래키와 매핑한다.
- **주문상품과 주문**: 다대일 양방향 관계다. 외래 키가 주문상품에 있으므로 주문상품이 연관관계의 주인이다. 그러므로 OrderItem.order 를 ORDER_ITEM.ORDER_ID 외래 키와 매핑한다.
- **주문상품과 상품**: 다대일 단방향 관계다. OrderItem.item 을 ORDER_ITEM.ITEM_ID 외래 키와 매핑한다.
- **주문과 배송**: 일대일 양방향 관계다. Order.delivery 를 ORDERS.DELIVERY_ID 외래 키와 매핑한다.
- **카테고리와 상품**: @ManyToMany 를 사용해서 매핑한다.(실무에서 @ManyToMany는 사용하지 말자. 여기서는 다대다 관계를 예제로 보여주기 위해 추가했을 뿐이다)
