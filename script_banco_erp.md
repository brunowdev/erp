# Script de criação do banco.

Para executar a aplicação, no PostgreSQL, criar um usuário chamado **ERP** e rodar o seguinte script:

```sql

CREATE TABLE USERS (

  ID          VARCHAR(20) NOT NULL,
  NOME        VARCHAR(50) NOT NULL,
  EMAIL       VARCHAR(50) NOT NULL,
  CPDWD       VARCHAR(32) NOT NULL,
  SITUACAO    CHAR        NOT NULL DEFAULT 'A',

  CONSTRAINT PK_USERS PRIMARY KEY (ID),
  CONSTRAINT CKU_EMAIL UNIQUE(EMAIL),
  CONSTRAINT CKV_CATEGORIAS_PRODUTO_SIT CHECK (SITUACAO IN ('A', 'I'))

);

CREATE TABLE CONTAS(
   ID                    SERIAL NOT NULL,
   CNPJ                  VARCHAR(14),
   RAZAO_SOCIAL          VARCHAR(125),
   NOME_FANTASIA         VARCHAR(50),
   SITUACAO              CHAR DEFAULT 'A' NOT NULL,

    PRIMARY KEY (ID),
    UNIQUE(CNPJ)
);

CREATE TABLE CATEGORIAS_PRODUTO(
   ID                   SERIAL NOT NULL,
   NOME                 VARCHAR(50) NOT NULL,
   DESCRICAO            VARCHAR(5000),
   SITUACAO             CHAR DEFAULT 'A' NOT NULL,

  CONSTRAINT PK_CATEGORIAS_PRODUTO PRIMARY KEY(ID),
  CONSTRAINT CKU_NOME UNIQUE(NOME),
  CONSTRAINT CKV_CATEGORIAS_PRODUTO_SIT CHECK (SITUACAO IN ('A', 'I'))

);

CREATE TABLE PRODUTOS(
   ID                      SERIAL NOT NULL,
   NOME                    VARCHAR(50) NOT NULL,
   DESCRICAO               VARCHAR(5000),
   SITUACAO                CHAR DEFAULT 'A' NOT NULL,
   QTD_DISPONIVEL          DECIMAL(7, 2) NOT NULL DEFAULT 0,
   VL_UNITARIO             DECIMAL(7, 2) NOT NULL DEFAULT 0,
   I_CATEGORIAS_PRODUTO    INTEGER NOT NULL,

   CONSTRAINT PK_PRODUTOS PRIMARY KEY(ID),
   CONSTRAINT FK_PRODUTOS_I_CATEGORIAS_PRO FOREIGN KEY(I_CATEGORIAS_PRODUTO) REFERENCES CATEGORIAS_PRODUTO(ID),
   CONSTRAINT CKV_PRODUTOS_SITUACAO CHECK (SITUACAO IN ('A', 'I')),
   CONSTRAINT CKU_PRODUTOS_NOME UNIQUE (NOME)

);

--ALGUNS DADOS PADRÕES

INSERT INTO USERS VALUES ('master', 'Master', 'master@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('fernando.boaglio', 'Master', 'fernando@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('elder.moraes', 'moraes', 'moraes@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('edson.yanaga', 'yanaga', 'yanaga@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('reza_rahman', 'reza_rahman', 'reza_rahman@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('thjanssen123', 'thjanssen123', 'thjanssen123@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('delabassee', 'delabassee', 'delabassee@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('otaviojava', 'otaviojava', 'otaviojava@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('gegastaldi', 'gegastaldi', 'gegastaldi@crm.com', 'EB0A191797624DD3A48FA681D3061212');
INSERT INTO USERS VALUES ('rcandidosilva', 'rcandidosilva', 'rcandidosilva@crm.com', 'EB0A191797624DD3A48FA681D3061212');

INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('RED HAT JBOSS MIDDLEWARE' , 'A fully certified Java™ EE 7 container that includes everything needed to build, run, and manage Java-based services.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('VIRTUALIZATION PLATFORM' , 'Complete enterprise virtualization management for servers and desktops on the same infrastructure.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('CLOUD COMPUTING' , 'A single-subscription offering that lets you build and manage an open, private Infrastructure-as-a-Service (IaaS) cloud and ease your way into a highly scalable, public-cloud-like infrastructure based on OpenStack®' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('STORAGE' , 'Open, software-defined file storage that combines reliable Red Hat software with x86 commodity hardware, eliminating the need for high-cost proprietary storage systems.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('MOBILE PLATFORM' , 'A platform that offers centralized control of security and back-end integration, collaborative app development, and a range of deployments that increase the speed of app integration with enterprise systems and delivery.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('MANAGEMENT' , 'The easiest way to manage your Red Hat infrastructure for efficient and compliant IT operations. Lets you establish trusted content repos and processes that help you build a standards-based, secure Red Hat environment.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('Red Hat Open Innovation Labs' , 'Speed up your next application development project. We’ll help your team make use of innovative open source technologies, rapidly build prototypes, do DevOps, and adopt agile. Get started immediately with hands-on guidance from Red Hat’s subject matter experts.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('Training' , 'Improve your in-demand technology skills.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('Certification' , 'Make yourself more marketable by certifying your skill level.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('Consulting' , 'Team up with us to collaboratively tackle your technology challenges.' );
INSERT INTO CATEGORIAS_PRODUTO (NOME, DESCRICAO) VALUES ('Support' , 'Get proactive, focused help from the best engineers in the industry.' );

INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (1, 'Red Hat Enterprise Linux' , 'Red Hat® Enterprise Linux® delivers military-grade security, 99.999% uptime, support for business-critical workloads, and so much more. Ultimately, the platform helps you reallocate resources from maintaining the status quo to tackling new challenges. It s just 1 reason why more than 90% of Fortune Global 500 companies use Red Hat products and solutions.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (1, 'Red Hat Satellite' , 'As your Red Hat® environment continues to grow, so does the need to manage it to a high standard of quality. Red Hat Satellite is an infrastructure management product specifically designed to keep Red Hat Enterprise Linux® environments and other Red Hat infrastructure running efficiently, properly secured, and compliant with various standards.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (1, 'Red Hat OpenStack Platform' , 'Your IT department is being challenged to react faster to growing customer demands. If you’re like many IT organizations, you’re exploring Infrastructure-as-a-Service (IaaS) private clouds, like OpenStack®, to swiftly deploy and scale IT infrastructure. But not just any OpenStack cloud can deliver the demands of a production-scale environment and meet your performance, scalability, and security standards. Fortunately, Red Hat® OpenStack Platform does all this and more.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (2, 'Red Hat JBoss Enterprise Application Platform' , 'Red Hat® JBoss® Enterprise Application Platform (JBoss EAP) delivers enterprise-grade security, performance, and scalability in any environment. Whether on-premise; virtual; or in private, public, or hybrid clouds, JBoss EAP can help you deliver apps faster, everywhere.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (2, 'Red Hat JBoss Web Server' , 'Your development team loves the flexibility of open source web servers and components, like Apache and Tomcat. But as your company grows, it demands that you move to a more secure, more stable environment with the enterprise-level features you need to deliver large-scale websites and lightweight web apps.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (2, 'Red Hat JBoss Data Grid' , 'Great user experience is ever-more dependent on application performance and quality. Even a few seconds delay can mean the difference between success and failure for a new business initiative. To capitalize on customer engagement, you need to know your customers and provide targeted offers that prompt them to interact in real time. Data bottlenecks are becoming more common as organizations need to process larger volumes, greater varieties, and a higher velocity of data to meet customer expectations and deliver personalized data-driven engagement.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (2, 'Red Hat JBoss Developer Studio' , 'Red Hat® JBoss® Developer Studio provides superior support for your entire development life cycle in one tool. It s a certified Eclipse-based integrated development environment (IDE) for developing, testing, and deploying rich web apps, mobile web apps, transactional enterprise apps, and microservices.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (3, 'Red Hat Virtualization' , 'Virtualization can vastly improve efficiency, free up resources, and cut costs. But you can t afford to sacrifice performance, security, and existing investments. And as you plan for future technologies like cloud and containers, it s important to build common services that use your virtualization investment while avoiding vendor lock-in. So what if you could virtualize both your servers and workstations, manage them from 1 simple interface, and build a foundation for future technologies on your terms—without compromise?'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (4, 'Red Hat Cloud Infrastructure' , 'Red Hat® Cloud Infrastructure is a combination of tightly integrated Red Hat technologies that lets you build and manage an open, private Infrastructure-as-a-Service (IaaS) cloud—at a much lower cost than alternative solutions. You can deploy any combination of these components in whatever way you need.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (4, 'Red Hat CloudForms' , 'Managing a complex, hybrid IT environment can require multiple management tools, redundant policy implementations, and extra staff to handle the operations. Red Hat® CloudForms simplifies IT, providing unified management and operations in a hybrid environment.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (4, 'Red Hat OpenShift' , 'Red Hat® OpenShift is a container application platform that brings docker and Kubernetes to the enterprise. Regardless of your applications architecture, OpenShift lets you easily and quickly build, develop, and deploy in nearly any infrastructure, public or private. Whether it’s on-premise, in a public cloud, or hosted, you have an award-winning platform to get your next big idea to market ahead of your competition.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (5, 'Red Hat Gluster Storage' , 'Red Hat® Gluster Storage is a software-defined storage (SDS) platform designed to handle the requirements of traditional file storage—high-capacity tasks like backup and archival as well as high-performance tasks of analytics and virtualization.But unlike traditional storage systems, Red Hat Gluster Storage isn’t rigid and expensive. It easily scales across bare metal, virtual, container, and cloud deployments'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (5, 'Red Hat Ceph Storage' , 'Red Hat® Ceph Storage is a massively scalable, programmable storage platform that supports cloud infrastructure, media repositories, backup and restore systems, and data lakes. It can free you from the expensive lock of proprietary, hardware-based storage solutions; consolidate labor and storage costs into 1 versatile solution; and introduce cost-effective scalability that modern workloads require.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (6, 'Red Hat Mobile Application Platform' , 'Red Hat Mobile Application Platform supports an agile approach to developing, integrating, and deploying enterprise mobile applications—whether native, hybrid, or on the web. The platform supports collaborative development across multiple teams and projects with a wide variety of leading tool kits and frameworks. You gain central control over security and policy management, the ease of Mobile Backend-as-a-Service (MBaaS) integration with enterprise systems, and a choice of cloud deployment options.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (7, 'Red Hat Satellite' , 'As your Red Hat® environment continues to grow, so does the need to manage it to a high standard of quality.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (7, 'Red Hat Insights' , 'Predictive analytics helps you see what’s happening in your IT environment. It also allows staff to fix technical issues before they impact your environment—avoiding costly downtime.'  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (8, 'Red Hat Red Hat Open Innovation Labs' , ''  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (9, 'Red Hat Training' , ''  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (10, 'Red Hat Certification' , ''  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (11, 'Red Hat Consulting' , ''  );
INSERT INTO PRODUTOS (I_CATEGORIAS_PRODUTO, NOME, DESCRICAO) VALUES (12, 'Red Hat Support' , ''  );

```
