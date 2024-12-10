//
//  ShareViewModel.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UniformTypeIdentifiers

// MARK: - ShareViewModel
final class ShareViewModel: ObservableObject {
    private let info: ShareViewInfo
    
    @Published var image = UIImage()
    @Published var description: String?
    
    init(with info: ShareViewInfo) {
        self.info = info
    }
}

// MARK: - Logic
extension ShareViewModel {
    func loadData() {
        loadImage()
        loadDescription()
    }
    
    func loadDescription() {
        description = nil
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) { [weak self] in
            self?.description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
        }
    }
    
    func dismiss() {
        info.extensionContext.completeRequest(returningItems: [])
    }
}

// MARK: - Logic
private extension ShareViewModel {
    func loadImage() {
        info.itemProvider.loadDataRepresentation(forTypeIdentifier: UTType.image.identifier) { data, error in
            guard let data, let image = UIImage(data: data) else { return }
            let aspectRatio = image.size.height / image.size.width
            let width = UIScreen.main.bounds.width - 40
            let height = width * aspectRatio
            let size = CGSize(width: width, height: height)
            guard let preview = image.preparingThumbnail(of: size) else { return }
            self.image = preview
        }
    }
}
